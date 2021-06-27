package com.project.patterndesignserver.controller.colorMatching;


import com.project.patterndesignserver.service.colorMatch.ColorMatchingService;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/colormatch")
public class ColorMatchingController {

    @Autowired
    ColorMatchingService colorMatchingService;
    @ApiOperation(value = "配对单张图片",tags = {"颜色匹配"},notes = "配对单张图片，返回配对后图的url，图片必须是公共素材库的，传入两个参数，分别为生成图的url" +
            "和参考图的url，示例：http://localhost:8081/server_resource/material/bird/1.png,前面需要有http，没有生成则返回400 statecode")
    @RequestMapping(value = "/matchOne",method = RequestMethod.GET)
    @ResponseBody
    public String colorMatching(String sourceUrl, String referenceUrl,
                                 @RequestParam(value = "k", required = false, defaultValue = "8")Integer k,
                                 @RequestParam(value = "mode", required = false, defaultValue = "match")String mode,
                                 HttpServletResponse response) {
        String res= colorMatchingService.MatchOne(sourceUrl, referenceUrl, k, mode);
        if(res == null){
            response.setStatus(400);
            //没有正确生成
        }
        return res;
    }
}