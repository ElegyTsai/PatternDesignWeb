package com.project.patterndesignserver.controller.image;

import com.project.patterndesignserver.service.image.UserImageService;
import com.project.patterndesignserver.util.result.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/img/user")
@Controller
public class UserImageController {
    @Autowired
    UserImageService userImageService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    Result<String> upload(@RequestParam("pic")MultipartFile multipartFile){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userImageService.upload(multipartFile,username);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    Result delete(@Param("UUID")String UUID){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userImageService.delete(UUID,username);
    }

    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    @ResponseBody
    Result queryAll(){
        return userImageService.queryByUser();
    }

    @RequestMapping(value = "/getImage/{url}",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable String url) throws IOException {
        return userImageService.getImage(url);
    }

    @RequestMapping(value = "/getNail/{url}",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getNail(@PathVariable String url) throws IOException{
        return userImageService.getNail(url);
    }

}
