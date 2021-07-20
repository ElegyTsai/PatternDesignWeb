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
    @Value("${python.mattingPool.workThread}")
    private int maxWorkThread;
    @Value("${python.mattingPool.waitThread}")
    private int maxWaitThread;

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

    public boolean renewTask(MattingTask mattingTask){
        writeLock.lock();
        MattingTask oldTask = getTask(mattingTask.getSessionId());
        oldTask.setStatusAsActive();
        oldTask.setCacheImage(mattingTask.getCacheImage());
        oldTask.updateTimeNow();
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
        rabbitmqTemplate.convertAndSend("mattingExchange","handler.1",mattingTask.serialized());
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
                rabbitmqTemplate.convertAndSend("mattingExchange","handler.1",task.serialized());
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
        rabbitmqTemplate.convertAndSend("mattingExchange","handler.1",mattingTask.serialized());
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
        rabbitmqTemplate.convertAndSend("mattingExchange","handler.1",mattingTask.serialized());
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
        rabbitmqTemplate.convertAndSend("mattingExchange","handler.1",mattingTask.serialized());
        return ret;
    }

    protected int LRURemove(){
        writeLock.lock();
        MattingTask mattingTask = LRUQueue.poll();
        mattingTask.setStatusAsPending();
        if(taskPool.getOrDefault(mattingTask.getSessionId(),null)!=null){
            taskPool.remove(mattingTask.getSessionId());
            waitPool.addIntoWaitPool(mattingTask);
            writeLock.unlock();
            //还需要一个队列通讯
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
