package com.project.patterndesignserver.module.pool;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class ExecutorPool {
    private int maxWorkThread = 1;
    private int maxWaitThread = 10;
    private int pySever = 2;
    private String routineKeyPrefix = "handler.";
    private String exchangeKey = "mattingExchange";

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    WaitPool waitPool ;
    private HashMap<Long,MattingTask> taskPool = new HashMap<>();
    //  sessionId-task
    private PriorityQueue<MattingTask> LRUQueue = new PriorityQueue<MattingTask>(new Comparator<MattingTask>() {
        @Override
        public int compare(MattingTask o1, MattingTask o2) {
            return o1.getLastUpdateTime()< o2.getLastUpdateTime()? 1: -1;
        }
    });
    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public ExecutorPool(){
        waitPool= new WaitPool(maxWaitThread);
    }
    private String loadBalance(long sessionId){
        int serverNo = (int)(sessionId%pySever) + 1;
        return routineKeyPrefix+serverNo;
    }

    public boolean renewTask(MattingTask mattingTask){
        writeLock.lock();
        MattingTask oldTask = getTask(mattingTask.getSessionId());
        oldTask.setStatusAsActive();
        oldTask.setCacheImage(mattingTask.getCacheImage());
        oldTask.updateTimeNow();
        oldTask.setOperationCount(mattingTask.getOperationCount());
        LRUQueue.remove(oldTask);
        LRUQueue.add(oldTask);
        writeLock.unlock();
        return true;
    }
    /* 队列通讯模块
     **
     */
    public void initializeToMQ(MattingTask mattingTask){
        mattingTask.taskInitialize();
        rabbitmqTemplate.convertAndSend(exchangeKey,loadBalance(mattingTask.getSessionId()),mattingTask.serialized());
    }

    public MattingTask getTask(long sessionId){
        readLock.lock();
        MattingTask task = taskPool.getOrDefault(sessionId,null);
        readLock.unlock();
        if(task==null){
            readLock.lock();
            task = waitPool.getTask(sessionId);
            readLock.unlock();
            if(task==null){
                return null;
            }else{
                writeLock.lock();
                waitPool.removeFromWaitPool(sessionId);
                addIntoPool(task);
                task.taskRedo();
                writeLock.unlock();
                rabbitmqTemplate.convertAndSend(exchangeKey,loadBalance(task.getSessionId()),task.serialized());
            }
        }

        return task;
    }

    public boolean addIntoPool(MattingTask mattingTask){
        while(isFull()){
            LRURemove();
        }
        writeLock.lock();
        mattingTask.updateTimeNow();
        LRUQueue.add(mattingTask);
        taskPool.put(mattingTask.getSessionId(),mattingTask);
        writeLock.unlock();
        //队列通讯
        return true;
    }
    //这个才是能使用的标准接口
    public boolean initializeTask(MattingTask mattingTask){
        writeLock.lock();
        addIntoPool(mattingTask);
        initializeToMQ(mattingTask);
        writeLock.unlock();
        return true;
    }
    public int reset(long sessionId){
        MattingTask mattingTask = getTask(sessionId);
        if(mattingTask==null) return -1;
        writeLock.lock();
        mattingTask.taskReset();
        int ret = mattingTask.getOperationCount();
        initializeToMQ(mattingTask);
        writeLock.unlock();
        return ret;
    }

    public int addClick(long sessionId,int x,int y,boolean mouse){
        MattingTask mattingTask = getTask(sessionId);
        if(mattingTask==null) return -1;
        writeLock.lock();
        mattingTask.updateTimeNow();
        LRUQueue.remove(mattingTask);
        LRUQueue.add(mattingTask);
        mattingTask.addClicks(x, y, mouse);
        int ret = mattingTask.getOperationCount();
        writeLock.unlock();
        rabbitmqTemplate.convertAndSend(exchangeKey,loadBalance(mattingTask.getSessionId()),mattingTask.serialized());
        return ret;
    }
    public int undo(long sessionId){
        MattingTask mattingTask = getTask(sessionId);
        if(mattingTask==null) return -1;
        writeLock.lock();
        mattingTask.updateTimeNow();
        LRUQueue.remove(mattingTask);
        LRUQueue.add(mattingTask);
        if(!mattingTask.undo()) return -2;
        int ret =mattingTask.getOperationCount();
        writeLock.unlock();
        // false表示撤销失败，click为空
        rabbitmqTemplate.convertAndSend(exchangeKey,loadBalance(mattingTask.getSessionId()),mattingTask.serialized());
        return ret;
    }
    public int mask(long sessionId){
        MattingTask mattingTask = getTask(sessionId);
        if(mattingTask==null) return -1;
        writeLock.lock();
        mattingTask.updateTimeNow();
        mattingTask.mask();
        LRUQueue.remove(mattingTask);
        LRUQueue.add(mattingTask);
        int ret = mattingTask.getOperationCount();
        writeLock.unlock();
        rabbitmqTemplate.convertAndSend(exchangeKey,loadBalance(mattingTask.getSessionId()),mattingTask.serialized());
        return ret;
    }

    protected int LRURemove(){
        writeLock.lock();
        MattingTask mattingTask = LRUQueue.poll();
        mattingTask.setStatusAsPending();
        if(taskPool.getOrDefault(mattingTask.getSessionId(),null)!=null){
            taskPool.remove(mattingTask.getSessionId());
            mattingTask.taskFinalize();
            waitPool.addIntoWaitPool(mattingTask);
            writeLock.unlock();
            rabbitmqTemplate.convertAndSend(exchangeKey,loadBalance(mattingTask.getSessionId()),mattingTask.serialized());
            return 1;
        }
        writeLock.unlock();
        return 0;
    }
    protected boolean isFull(){

        readLock.lock();
//        System.out.println(Thread.currentThread().getName()+" get readlock");
        int size = taskPool.size();
        readLock.unlock();
//        System.out.println(Thread.currentThread().getName()+" release readlock");
        return size>=maxWorkThread;

    }

    protected int size(){
        readLock.lock();
        int size = taskPool.size();
        readLock.unlock();
        return size;
    }
}


class WaitPool{
    private int maxWaitThread;
    private HashMap<Long,MattingTask> pool = new HashMap<>();
    //  sessionId-task
    private PriorityQueue<MattingTask> LRUQueue = new PriorityQueue<MattingTask>(new Comparator<MattingTask>() {
        @Override
        public int compare(MattingTask o1, MattingTask o2) {
            return o1.getLastUpdateTime()< o2.getLastUpdateTime()? 1: -1;
        }
    });
    public WaitPool(int maxWaitThread){
        this.maxWaitThread=maxWaitThread;
    }
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public int addIntoWaitPool(MattingTask mattingTask){

        while(size()>=maxWaitThread){
            removeOne();
        }
        writeLock.lock();
        if(pool.containsKey(mattingTask.getSessionId())){
            removeFromWaitPool(mattingTask.getSessionId());
        }
        pool.put(mattingTask.getSessionId(),mattingTask);
        LRUQueue.add(mattingTask);
        writeLock.unlock();
        return 1;
    }

    public MattingTask getTask(long sessionId){
        readLock.lock();
        MattingTask task = pool.getOrDefault(sessionId,null);
        readLock.unlock();
        return task;
    }

    public MattingTask removeFromWaitPool(long sessionId){
        writeLock.lock();
        if(pool.containsKey(sessionId)){
            MattingTask task = getTask(sessionId);
            pool.remove(sessionId);
            LRUQueue.remove(task);
            writeLock.unlock();
            return task;
        }
        writeLock.unlock();
        return null;

    }
    public int removeOne(){
        writeLock.lock();
        if(pool.isEmpty()){
            writeLock.unlock();
            return 0;
        }
        MattingTask mattingTask = LRUQueue.poll();
        this.pool.remove(mattingTask.getSessionId());
        writeLock.unlock();
        return 1;
    }
    public int size(){
        readLock.lock();
        int size= pool.size();
        readLock.unlock();
        return size;
    }
}
