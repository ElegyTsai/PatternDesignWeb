package com.project.patterndesignserver.controller.image;

import com.project.patterndesignserver.model.content.image.PublicImage;
import com.project.patterndesignserver.model.content.image.PublicImageResult;
import com.project.patterndesignserver.service.image.SysImageService;
import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "公共素材管理")
@Controller
@RequestMapping("api/img/public")
public class PublicImageController {
    @Autowired
    SysImageService sysImageService;

    @ApiOperation(value = "上传图片到公共素材库",tags = {"公共素材管理"},notes = "Post方法，pic存放图片，tag是图片所属的种类" +
            "permission是赋予的权限默认为all，返回信息为图片的高清图url,理论上来说只有管理员才能上传，为了便于测试没有添加授权验证")
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result<String> upload(@RequestParam("pic")MultipartFile multipartFile, @RequestParam("tag") String tag
            ,@RequestParam(value = "permission",defaultValue = "all")String permission){
        return sysImageService.uploadImage(multipartFile,tag,permission);
    }

    @ApiOperation(value = "根据图片id删除图片",tags = {"公共素材管理"},notes = "id是数据库的id，（这个api没什么用）" )
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(@Param("id") long id){
        return sysImageService.deleteImage(id);
    }

    @ApiOperation(value = "查看高清图片",tags = {"公共素材管理"},notes = "get方法，需要tag和图片的UUID" +
            "用byte数组传递图片")
    @RequestMapping(value = "/getImage/{tag}/{url}",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable String tag,@PathVariable String url,HttpServletResponse response) throws IOException{
        try{
            byte [] res = sysImageService.getImage(tag,url);
            return  res;
        }
        catch (Exception e){
            response.setStatus(404);
            return null;
        }
    }

    @ApiOperation(value = "查看缩略图片",tags = {"公共素材管理"},notes = "get方法，需要tag和图片的UUID" +
            "用byte数组传递图片")
    @RequestMapping(value = "/getNail//{tag}/{url}",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getNail(@PathVariable String tag, @PathVariable String url, HttpServletResponse response) throws IOException{
        try{
            byte [] res = sysImageService.getNail(tag,url);
            return  res;
        }
        catch (Exception e){
            response.setStatus(404);
            return null;
        }
    }
    @ApiIgnore
    @RequestMapping(value = "/queryByTagsAnd",method = RequestMethod.POST)
    @ResponseBody
    public List<String> queryTagAnd(@RequestBody List<String> tags){
        return sysImageService.queryImageByTagsAnd(tags);
    }
    @ApiIgnore
    @RequestMapping(value = "/queryByTagsOr",method = RequestMethod.POST)
    @ResponseBody
    public List<String> queryTagOr(@RequestBody List<String> tags){
        return sysImageService.queryImageByTagsOr(tags);
    }


    @ApiOperation(value = "根据图片分类查询图片",tags = {"公共素材管理"},notes = "get方法，需要提供tag" +
            "返回一个列表，列表里的每个对象包含了一张图片的高清图url和缩略图url，还有图片自己的文件名")
    @RequestMapping(value = "/queryByTag",method = RequestMethod.GET)
    @ResponseBody
    public List<PublicImageResult> queryTag(@Param("tag") String tag){
        return sysImageService.queryTag(tag);
    }
}
