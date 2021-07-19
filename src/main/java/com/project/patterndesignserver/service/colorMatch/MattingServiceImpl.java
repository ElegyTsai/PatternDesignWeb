package com.project.patterndesignserver.service.colorMatch;

import com.project.patterndesignserver.module.pool.ExecutorPool;
import com.project.patterndesignserver.module.pool.MattingTask;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MattingServiceImpl implements MattingService{

    @Value("${image.cacheSave.path}")
    private String path;

    @Autowired
    ExecutorPool executorPool;

    @Async
    public void upload(DeferredResult<Result<Long>> deferredResult,MultipartFile file){
        MattingTask mattingTask;
        try{
            mattingTask = new MattingTask(file,path);
        }
        catch (IOException ioException){
            Result<Long> res = new Result<>(ExceptionMsg.SuffixError);
            deferredResult.setResult(res);
            return;
        }
        catch (Exception e) {
            Result<Long> res = new Result<>(ExceptionMsg.FAIL);
            deferredResult.setResult(res);
            return;
        }
        long sessionId = mattingTask.getSessionId();
        int operationCnt = mattingTask.getOperationCount();
        executorPool.initializeTask(mattingTask);
        MattingTask finishedTask = executorPool.getTask(sessionId);
        while(finishedTask.getOperationCount()<operationCnt || (finishedTask.getOperationCount()==operationCnt && finishedTask.taskIsRunning()))
        {
            try{
                Thread.yield();
                Thread.sleep(10);}
            catch (Exception e){
                Result<Long> res = new Result<>(ExceptionMsg.FAIL);
                deferredResult.setResult(res);
                return;
            }
            finishedTask = executorPool.getTask(sessionId);
        }
        Result<Long> res = new Result<>();
        res.setData(sessionId);
        deferredResult.setResult(res);
        return;
    }


    @Async
    public void addClick(DeferredResult<Result<byte[]>> deferredResult,long sessionId,int x,int y,boolean mouse){
        int operationCnt = executorPool.addClick(sessionId,x,y,mouse);
        if(operationCnt==-1){
            deferredResult.setResult(new Result<>(ExceptionMsg.OutdatedSession));
        }

        MattingTask finishedTask = executorPool.getTask(sessionId);
        while(finishedTask.getOperationCount()<operationCnt || (finishedTask.getOperationCount()==operationCnt && finishedTask.taskIsRunning()))
        {
            try{
                Thread.yield();
                Thread.sleep(10);}
            catch (Exception e){
                Result<byte []> res = new Result<>(ExceptionMsg.FAIL);
                deferredResult.setResult(res);
                return;
            }
            finishedTask = executorPool.getTask(sessionId);
        }
        Result<byte []> res = new Result<>();
        res.setData(finishedTask.getCacheImage());
        deferredResult.setResult(res);
        return;
    }

    @Async
    public void undo(DeferredResult<Result<byte[]>> deferredResult,long sessionId){
        int operationCnt = executorPool.undo(sessionId);
        if(operationCnt==-1){
            deferredResult.setResult(new Result<>(ExceptionMsg.OutdatedSession));
        }

        MattingTask finishedTask = executorPool.getTask(sessionId);
        while(finishedTask.getOperationCount()<operationCnt || (finishedTask.getOperationCount()==operationCnt && finishedTask.taskIsRunning()))
        {
            try{
                Thread.yield();
                Thread.sleep(10);}
            catch (Exception e){
                Result<byte []> res = new Result<>(ExceptionMsg.FAIL);
                deferredResult.setResult(res);
                return;
            }
            finishedTask = executorPool.getTask(sessionId);
        }
        Result<byte []> res = new Result<>();
        res.setData(finishedTask.getCacheImage());
        deferredResult.setResult(res);
        return;
    }

    public void updateState(String msg){
        MattingTask mattingTask = MattingTask.deserialized(msg);
        executorPool.renewTask(mattingTask);
    }

}
