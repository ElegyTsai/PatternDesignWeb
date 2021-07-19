package com.project.patterndesignserver.controller.colorMatching;

import com.project.patterndesignserver.service.colorMatch.MattingService;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/matting")
public class MattingController {

    @Autowired
    MattingService mattingService;
    @ApiOperation(value = "上传图片" ,tags = {"智能抠图"},notes = "上传一张图片到服务器,返回一个后续使用的sessionId")
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public DeferredResult<Result<Long>> upload(MultipartFile file){

        System.out.println(Thread.currentThread().getName() + "进入异步方法");
        DeferredResult<Result<Long>> deferredResult = new DeferredResult<>();
        mattingService.upload(deferredResult,file);
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                Result<Long> res = new Result<>(ExceptionMsg.ServerBusy);
                deferredResult.setResult(res);
            }
        });
        System.out.println(Thread.currentThread().getName() + "退出异步方法");
        return deferredResult;
    }
}
