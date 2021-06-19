package com.project.patterndesignserver.controller.material;


import com.project.patterndesignserver.service.image.LatestUsedMaterialsService;
import com.project.patterndesignserver.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("api/material/log")
public class LatestUsedMaterialController {
    @Autowired
    LatestUsedMaterialsService latestUsedMaterialsService;

    @ApiOperation(value = "添加一个最近使用的素材",tags = {"素材使用记录"},notes = "需要带着token才能记录用户身份，但是这个api本身不需要授权" +
            "授权，未登陆可以访问但是不会被记录，只需要get/post一个url即可")
    @RequestMapping("/add")
    @ResponseBody
    public Result<String> add(String url){

        return latestUsedMaterialsService.add(url);
    }

    @ApiOperation(value = "返回最近使用的素材",tags = {"素材使用记录"},notes = "需要带着token才能找到，但是这个api本身不需要，" +
            "授权，未登陆可以访问但是不会被记录，只需要get/post一个url即可")
    @RequestMapping("/query")
    @ResponseBody
    public Result<List> query(@RequestParam(value = "rank",defaultValue = "50") int rank){
        return latestUsedMaterialsService.query(rank);
    }
}