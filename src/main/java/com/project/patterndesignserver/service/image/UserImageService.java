package com.project.patterndesignserver.service.image;


import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageService {

    Result upload(MultipartFile multipartFile,String username);

    Result delete(String UUID,String username);

    Result queryByUser();

    byte[] getImage(String url);

    byte[] getNail(String url);


}
