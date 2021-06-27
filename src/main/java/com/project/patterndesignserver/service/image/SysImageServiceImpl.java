package com.project.patterndesignserver.service.image;

import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.content.image.PublicImageMapper;
import com.project.patterndesignserver.model.content.image.PublicImage;
import com.project.patterndesignserver.model.content.image.PublicImageResult;
import com.project.patterndesignserver.util.MD5Util;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import net.coobird.thumbnailator.Thumbnails;
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
    @Value("${image.hd.url}")
    private String urlPrefix;
    @Value("${image.nail.url}")
    private String nailUrlPrefix;
    @Value("${image.separator}")
    private String separator;
    @Override
    public Result<String> uploadImage(MultipartFile multipartFile, String tag, String permission){

        PublicImage image = new PublicImage();
        String originalName = multipartFile.getOriginalFilename();
        String suffix =originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
        if(!suffix.equals(".png") && !suffix.equals(".jpg")){
            return new Result<String>(ExceptionMsg.SuffixError);
        }
        image.setTag(tag);
        image.setPermission(permission);
        String newPath = path+tag+separator;
        String newFileName = UUID.randomUUID().toString().replace("-","")+suffix;

        try{
            //生成缩略图并保存起来
            File file = new File(newPath,newFileName);
            if(!file.getParentFile().exists()){
                if(!file.getParentFile().mkdirs()){
                    throw new Exception();
                }
            }

            image.setImagePath(newPath+newFileName);
            multipartFile.transferTo(file);
//            System.out.println(newFileName);
//            System.out.println(newPath);
            Thumbnails.of(newPath+newFileName)
                    .size(128, 128)
                    .toFile(newPath+"th_"+newFileName);

            image.setImageName(newFileName);
            image.setAvailable(true);
            image.setThumbnailPath(newPath+"th_"+newFileName);

            publicImageMapper.addImage(image);

            Result<String> result = new Result<>();
            result.setData(urlPrefix+tag+"/"+newFileName);

            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            Result<String> result = new Result<String>(ExceptionMsg.UploadFailed);
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
    public byte[] getImage(String tag,String url) throws IOException {
        File file = new File(path+tag+separator+url);
        FileInputStream inputStream = new FileInputStream(file);
        byte [] bytes = new byte[inputStream.available()];
        inputStream.read(bytes,0, inputStream.available());
        return bytes;
    }

    @Override
    public byte[] getNail(String tag,String url) throws IOException {
        File file = new File(path+tag+separator+"th_"+url);
        FileInputStream inputStream = new FileInputStream(file);
        byte [] bytes = new byte[inputStream.available()];
        inputStream.read(bytes,0, inputStream.available());
        return bytes;
    }

    @Override
    public List<PublicImageResult> queryTag(String tag) {
        List <PublicImageResult> results = new ArrayList<>();
        List <PublicImage> query = publicImageMapper.queryByTag(tag);
        for(PublicImage image : query){
            PublicImageResult result = new PublicImageResult();
//            String category = image.getTag();
            String fileName = image.getImageName();
            result.setUUID(fileName);
            result.setImageUrl(urlPrefix+tag+"/"+fileName);
            result.setThumbNailUrl(nailUrlPrefix+tag+"/"+fileName);
            results.add(result);
        }
        return results;
    }


}
