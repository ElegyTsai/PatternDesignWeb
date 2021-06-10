package com.project.patterndesignserver.content;

import com.project.patterndesignserver.mapper.content.image.UserImageMapper;
import com.project.patterndesignserver.model.content.image.PublicImage;
import com.project.patterndesignserver.model.content.image.UserImage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserImageDBTest {
    @Autowired
    UserImageMapper userImageMapper;

    @Test
    public void upload(){
        UserImage Image = new UserImage();
        Image.setImageName("testpic4");
        Image.setAvailable(true);

        Image.setMD5("adasdsdssr");
        Image.setImagePath("/home/desktop/");
        Image.setThumbnailPath("/home/desktop/1");
        Image.setUserId(11);
        userImageMapper.addImage(Image);
        userImageMapper.saveOwner(Image);
    }
    @Test
    public void save(){
        UserImage Image = new UserImage();
        Image.setId(4);
        Image.setUserId(11);
        userImageMapper.saveOwner(Image);
    }

    @Test
    public void queryMD5(){
        UserImage Image = userImageMapper.queryByMD5("adasdasd");
        System.out.println(Image.getImageName());
    }

    @Test
    public void queryByUser(){
        List<UserImage> images = userImageMapper.queryByUserId(11);
//        System.out.println(images.get(0).getRelationId());
        System.out.println(images.get(0).getUserId());
    }

    @Test
    public void delete1(){
//        userImageMapper.deleteRelation(3);
    }
    @Test
    public void delete2(){
        userImageMapper.deleteAllRelation(11);
    }

}
