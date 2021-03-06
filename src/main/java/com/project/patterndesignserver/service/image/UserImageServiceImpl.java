package com.project.patterndesignserver.service.image;

import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.content.image.UserImageMapper;
import com.project.patterndesignserver.model.content.image.PublicImage;
import com.project.patterndesignserver.model.content.image.UserImage;
import com.project.patterndesignserver.model.content.image.UserImageResult;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.util.MD5Util;
import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserImageServiceImpl extends BaseController implements UserImageService {
    @Autowired
    UserImageMapper userImageMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Value("${image.save.path}")
    private String path;


    private String urlPrefix = "localhost:8080/img/user/getImage/";

    @Override
    public Result upload(MultipartFile multipartFile,String username) {
        UserImage image;
        String originalName = multipartFile.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString().replace("-","")+originalName.substring(originalName.lastIndexOf("."));

        try{
            String MD5 = MD5Util.getMD5(multipartFile.getBytes());
            image = userImageMapper.queryByMD5(MD5);
            //????????????????????????????????????????????????
            if(image==null){
                image = new UserImage();
                File file = new File(path,newFileName);
                //??????????????????????????????????????????????????????????????????
                BufferedImage bufImage = ImageIO.read(multipartFile.getInputStream());
                Image thumb = bufImage.getScaledInstance(128,128,Image.SCALE_FAST);
                File thumbFile = new File(path,"th_"+newFileName);
                BufferedImage bi = new BufferedImage
                        (thumb.getWidth(null),thumb.getHeight(null),BufferedImage.TYPE_INT_RGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(thumb, 0, 0, null);
                bg.dispose();
                //???????????????????????????????????????????????????
                ImageIO.write(bi,newFileName.substring(newFileName.lastIndexOf(".")+1),thumbFile);

                //??????????????????????????????????????????????????????
                image.setImageName(newFileName);
                image.setAvailable(true);
                image.setThumbnailPath(path+"th_"+newFileName);
                image.setMD5(MD5);
                image.setImagePath(path+newFileName);
                multipartFile.transferTo(file);//????????????
                userImageMapper.addImage(image);//?????????????????????
            }
            //????????????????????????security?????????
            //???controller ???principle?????????
            User user = (User) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("user_"+username)),User.class);
            image.setUUID(newFileName);
            image.setMyGroup("Default");
            image.setUserId(user.getId());
            userImageMapper.saveOwner(image);
            Result<UserImageResult> result = new Result<>();
            result.setCode("200");
            UserImageResult imageResult = new UserImageResult();
            imageResult.setGroup(image.getMyGroup());
            imageResult.setUUID(newFileName);
            imageResult.setUrl(urlPrefix+image.getUUID());
            //?????????????????????????????????
            result.setData(imageResult);
            result.setMsg("????????????");
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            Result<String> result = new Result<>();
            result.setCode("400");
            result.setMsg("????????????");
            return result;
        }
    }

    @Override
    public Result delete(String UUID,String username) {
        User user = (User) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("user_"+username)),User.class);
        long userId = user.getId();
        try{
            if(userImageMapper.deleteRelation(UUID,userId)==0){
                Result<String> result = new Result<>();
                result.setCode("401");
                result.setMsg("????????????,?????????????????????????????????");
                return result;
            }
            Result<String> result = new Result<>();
            result.setCode("200");
            result.setMsg("????????????");
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            Result<String> result = new Result<>();
            result.setCode("400");
            result.setMsg("????????????");
            return result;
        }
    }

    @Override
    public Result queryByUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = (User) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("user_" + username)), User.class);
            long userId = user.getId();
            java.util.List<UserImage> userImages = userImageMapper.queryByUserId(userId);
            List<UserImageResult> imageResults = new ArrayList<>();
            for(UserImage  userImage: userImages){
                UserImageResult imageResult = new UserImageResult();
                imageResult.setGroup(userImage.getMyGroup());
                imageResult.setUUID(userImage.getUUID());
                imageResult.setUrl(urlPrefix+userImage.getUUID());
                imageResults.add(imageResult);
            }
            Result<List> result=new Result<List>();
            result.setMsg("????????????");
            result.setCode("200");
            result.setData(imageResults);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            Result<String> result = new Result<>();
            result.setCode("400");
            result.setMsg("????????????");
            return result;
        }

    }

    @Override
    public byte[] getImage(String url) {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = (User) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("user_" + username)), User.class);
            long userId = user.getId();
            UserImage image = userImageMapper.selectPic(url,userId);
            if(image==null) return null;
            File file = new File(image.getImagePath());
            FileInputStream inputStream = new FileInputStream(file);
            byte [] bytes = new byte[inputStream.available()];
            inputStream.read(bytes,0, inputStream.available());
            return bytes;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public byte[] getNail(String url) {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = (User) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("user_" + username)), User.class);
            long userId = user.getId();
            UserImage image = userImageMapper.selectPic(url,userId);
            if(image==null) return null;
            File file = new File(image.getThumbnailPath());
            FileInputStream inputStream = new FileInputStream(file);
            byte [] bytes = new byte[inputStream.available()];
            inputStream.read(bytes,0, inputStream.available());
            return bytes;
        }
        catch (Exception e){
            return null;
        }
    }

}
