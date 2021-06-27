package com.project.patterndesignserver.service.matrial;

import com.project.patterndesignserver.mapper.content.template.PublicTemplateMapper;
import com.project.patterndesignserver.model.content.template.PublicTemplate;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PublicTemplateServiceImpl implements PublicTemplateService{
    @Autowired
    PublicTemplateMapper publicTemplateMapper;

    @Value("${serverdata.path}")
    private String path;
    @Value("${image.separator}")
    private String separator;
    @Value("${template.nail.url}")
    private String nailUrl;
    @Value("${template.json.url}")
    private String jsonUrl;

    @Override
    public Result<PublicTemplate> upload(MultipartFile thumbnail, String tag, String jsonText) {
        String originalName = thumbnail.getOriginalFilename();
        String suffix =originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
        if(!suffix.equals(".png") && !suffix.equals(".jpg")){
            return new Result<PublicTemplate>(ExceptionMsg.SuffixError);
        }
        String uid = UUID.randomUUID().toString().replace("-","");
        String savePath = path+"template"+separator+tag;
        String thumbnailFileName = uid+suffix;
        String jsonFileName = uid+".json";
        PublicTemplate publicTemplate = new PublicTemplate();
        try{
            File file = new File(savePath,thumbnailFileName);
            if(!file.getParentFile().exists()){
                if(!file.getParentFile().mkdirs()){
                    throw new Exception();
                }
            }
            thumbnail.transferTo(file);
            FileWriter fileWriter = new FileWriter(path+"template"+separator+tag+separator+jsonFileName);
            fileWriter.write(jsonText);
            fileWriter.flush();
            fileWriter.close();

            publicTemplate.setThumbnailUrl(nailUrl+tag+"/"+uid+suffix);
            publicTemplate.setTag(tag);
            publicTemplate.setUrl(jsonUrl+tag+"/"+uid+".json");
            publicTemplateMapper.addTemplate(publicTemplate);
            Result<PublicTemplate> res = new Result<>();
            res.setData(publicTemplate);
            return res;
        }catch (Exception e){
            return new Result<> (ExceptionMsg.FAIL);
        }
    }

    @Override
    public Result<List> queryByTag(String tag) {
        List<PublicTemplate> data = publicTemplateMapper.queryByTag(tag);
        Result<List> res = new Result<>();
        res.setData(data);
        return res;
     }

    @Override
    public byte[] getFile(String tag,String url) throws IOException {
        File file = new File(path+"template"+separator+tag+separator+url);
        FileInputStream inputStream = new FileInputStream(file);
        byte [] bytes = new byte[inputStream.available()];
        inputStream.read(bytes,0, inputStream.available());
        return bytes;
    }


}
