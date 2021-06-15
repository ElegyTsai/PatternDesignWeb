package com.project.patterndesignserver.controller.image;

import com.project.patterndesignserver.service.image.SysImageService;
import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("api/img/public")
public class PublicImageController {
    @Autowired
    SysImageService sysImageService;

    @RequestMapping("/upload")
    @ResponseBody
    public Result<String> upload(@RequestParam("pic")MultipartFile multipartFile, @RequestParam("tag") String tag
            , @RequestParam("permission") String permission){
        return sysImageService.uploadImage(multipartFile,tag,permission);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(@Param("id") long id){
        return sysImageService.deleteImage(id);
    }

    @RequestMapping(value = "/getImage/{url}",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable String url) throws IOException{
        return sysImageService.getImage(url);
    }

    @RequestMapping(value = "/getNail/{url}",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getNail(@PathVariable String url) throws IOException{
        return sysImageService.getNail(url);
    }

    @RequestMapping(value = "queryByTagsAnd",method = RequestMethod.POST)
    @ResponseBody
    public List<String> queryTagAnd(@RequestBody List<String> tags){
        return sysImageService.queryImageByTagsAnd(tags);
    }

    @RequestMapping(value = "queryByTagsOr",method = RequestMethod.POST)
    @ResponseBody
    public List<String> queryTagOr(@RequestBody List<String> tags){
        return sysImageService.queryImageByTagsOr(tags);
    }
}
