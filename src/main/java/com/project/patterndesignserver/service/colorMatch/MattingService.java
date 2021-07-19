package com.project.patterndesignserver.service.colorMatch;

import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;


public interface MattingService {
    public void upload(DeferredResult<Result<Long>> deferredResult, MultipartFile file);

    public void addClick(DeferredResult<Result<byte[]>> deferredResult,long sessionId,int x,int y,boolean mouse);

    public void updateState(String msg);

    public void undo(DeferredResult<Result<byte[]>> deferredResult,long sessionId);
}
