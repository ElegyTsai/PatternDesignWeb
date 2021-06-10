package com.project.patterndesignserver.service.image;

import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.content.image.PublicImageMapper;
import com.project.patterndesignserver.model.content.image.PublicImage;
import com.project.patterndesignserver.util.MD5Util;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SysImageServiceImpl extends BaseController implements SysImageService {
    @Autowired
    PublicImageMapper publicImageMapper;

    @Value("${image.save.path}")
    private String path;
    @Value("${image.url}")
    private String urlPrefix;

    @Override
    public Result<String> uploadImage(MultipartFile multipartFile, String tag, String permission){


        PublicImage image = new PublicImage();
        String originalName = multipartFile.getOriginalFilename();
        image.setTag(tag);
        image.setPermission(permission);
        String newFileName = UUID.randomUUID().toString().replace("-","")+originalName.substring(originalName.lastIndexOf("."));
        System.out.println(newFileName);
        try{
            //生成缩略图并保存起来
            File file = new File(path,newFileName);

            image.setImagePath(path+newFileName);
            BufferedImage bufImage = ImageIO.read(multipartFile.getInputStream());
            Image thumb = bufImage.getScaledInstance(128,128,Image.SCALE_FAST);
            File thumbFile = new File(path,"th_"+newFileName);

            BufferedImage bi = new BufferedImage
                    (thumb.getWidth(null),thumb.getHeight(null),BufferedImage.TYPE_INT_RGB);
            Graphics bg = bi.getGraphics();
            bg.drawImage(thumb, 0, 0, null);
            bg.dispose();
            ImageIO.write(bi,newFileName.substring(newFileName.lastIndexOf(".")+1),thumbFile);
            image.setImageName(newFileName);
            image.setAvailable(true);
            image.setThumbnailPath(path+"th_"+newFileName);
            image.setMD5(MD5Util.getMD5(multipartFile.getBytes()));
            multipartFile.transferTo(file);
            publicImageMapper.addImage(image);


            Result<String> result = new Result<>();
            result.setCode(200);
            result.setData(urlPrefix+newFileName);
            result.setMsg("上传成功");
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            Result<String> result = new Result<>();
            result.setCode(400);
            result.setMsg("上传失败");
            return result;
        }
    }

    @Override
    public Response deleteImage(long id){
        try{
            PublicImage image = publicImageMapper.selectImageById(id);
            if (image == null){
                return result();
            }
            File thFile = new File(image.getThumbnailPath());
            thFile.delete();
            File imageFile = new File(image.getImagePath());
            imageFile.delete();
            publicImageMapper.deleteImage(id);
            return result();
        }
        catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.FAIL);
        }
    }

    @Override
    public java.util.List<String> queryImageByTagsAnd(java.util.List<String> tags) {
        List<PublicImage> imgInfo = publicImageMapper.queryByTagsAnd(tags);
        List<String> res = new ArrayList<>();
        for(PublicImage img : imgInfo){
            res.add(urlPrefix+img.getImageName());
        }
        return res;
    }

    @Override
    public List<String> queryImageByTagsOr(List<String> tags) {
        List<PublicImage> imgInfo = publicImageMapper.queryByTagsOr(tags);
        List<String> res = new ArrayList<>();
        for(PublicImage img : imgInfo){
            res.add(urlPrefix+img.getImageName());
        }
        return res;
    }

    @Override
    public byte[] getImage(String url) throws IOException {
        File file = new File(path+url);
        FileInputStream inputStream = new FileInputStream(file);
        byte [] bytes = new byte[inputStream.available()];
        inputStream.read(bytes,0, inputStream.available());
        return bytes;
    }

    @Override
    public byte[] getNail(String url) throws IOException {
        File file = new File(path+"th_"+url);
        FileInputStream inputStream = new FileInputStream(file);
        byte [] bytes = new byte[inputStream.available()];
        inputStream.read(bytes,0, inputStream.available());
        return bytes;
    }


}
