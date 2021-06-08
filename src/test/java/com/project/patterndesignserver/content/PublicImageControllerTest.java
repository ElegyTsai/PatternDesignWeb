package com.project.patterndesignserver.content;

import com.project.patterndesignserver.service.image.SysImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublicImageControllerTest {
    @Autowired
    SysImageService sysImageService;

    @Test
    public void uploadTest(){
        File file = new File("/Users/elegy/Downloads/IMG_1367.JPG");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("pic", file.getName(), "png", fileInputStream);
            sysImageService.uploadImage(multipartFile,"shoujo","all");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public  void deleteTest(){
        sysImageService.deleteImage(14);
    }

}
