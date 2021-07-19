package com.project.patterndesignserver.util;

import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.UUID;

public class ImageUtil {
    @Value("${image.separator}")
    public static String separator;
    public static String saveWithRandomName(MultipartFile multipartFile,String path) throws IOException,Exception {
        String originalName = multipartFile.getOriginalFilename();
        String suffix =originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
        if(!suffix.equals(".png") && !suffix.equals(".jpg")){
            throw new Exception("unsupported file type");
        }
        String newFileName = UUID.randomUUID().toString().replace("-","")+suffix;
        File file = new File(path,newFileName);
        if(!file.getParentFile().exists()){
            if(!file.getParentFile().mkdirs()){
                throw new Exception();
            }
        }
        multipartFile.transferTo(file);
        return path+newFileName;
    }
}
