package com.project.patterndesignserver.controller.material;

import com.project.patterndesignserver.model.content.template.PublicTemplate;
import com.project.patterndesignserver.service.matrial.PublicTemplateService;
import com.project.patterndesignserver.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("api/template")
public class PublicTemplateController {
    @Autowired
    PublicTemplateService publicTemplateService;

    @ApiOperation(value = "上传模板",tags = {"模板管理","public-template-controller"},notes = "Post方法，thumbnail放缩略图，tag是模板所属的种类" +
            "返回信息为模板的jsonurl和缩略图url,理论上来说只有管理员才能上传，为了便于测试没有添加授权验证")
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result<PublicTemplate> upload(MultipartFile thumbnail, String tag, String jsonText){
        return publicTemplateService.upload(thumbnail,tag,jsonText);
    }

    @ApiOperation(value = "查询模板",tags = {"模板管理","public-template-controller"},notes = "【目前这个方法已经被废弃 】get方法，用于查找种类下的模板" +
            "返回信息为模板List，里面有jsonurl和缩略图url")
    @RequestMapping(value = "/queryByTag",method = RequestMethod.GET)
    @ResponseBody
    public Result<List> query(String tag){
        return publicTemplateService.queryByTag(tag);
    }

    @ApiOperation(value = "获取模板缩略图",tags = {"模板管理","public-template-controller"},notes = "【目前这个方法已经被废弃 】get方法，获取模板的缩略图" )
    @RequestMapping(value = "/getNail/{tag}/{fileName}",method = RequestMethod.GET,produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getNail(@PathVariable String tag, @PathVariable String fileName, HttpServletResponse response){
        try{
            byte [] res = publicTemplateService.getFile(tag,fileName);
            return  res;
        }
        catch (Exception e){
            response.setStatus(404);
            return null;
        }
    }
    @ApiOperation(value = "获取模板的json信息",tags = {"模板管理","public-template-controller"},notes = "get方法，获取模板的json信息，直接使用发来的url即可" )
    @RequestMapping(value = "/getJson/{tag}/{fileName}",method = RequestMethod.GET)
    @ResponseBody
    public String getJson(@PathVariable String tag, @PathVariable String fileName, HttpServletResponse response){
        try{
            byte [] res = publicTemplateService.getFile(tag,fileName);
            return new String(res);
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(404);
            return null;
        }
    }
}
