package com.project.patterndesignserver.service.matrial;

import com.project.patterndesignserver.model.content.template.PublicTemplate;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicTemplateService {
    public Result<PublicTemplate> upload(MultipartFile thumbnail,String tag,String jsonText);

    public Result<List> queryByTag(String tag);

    public byte[] getFile(String tag,String url) throws Exception;

}
