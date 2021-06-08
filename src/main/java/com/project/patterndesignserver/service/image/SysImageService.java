package com.project.patterndesignserver.service.image;

import com.project.patterndesignserver.util.result.Response;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface SysImageService {

    public Result<String> uploadImage(MultipartFile multipartFile, String tag, String permission);

    public Response deleteImage(long id);

    public List<String> queryImageByTagsAnd(List<String> tags);

    public List<String> queryImageByTagsOr(List<String> tags);

    public byte[] getImage(String url) throws IOException;

    public byte[] getNail(String url) throws IOException;


}
