package com.project.patterndesignserver.content;

import com.project.patterndesignserver.mapper.content.image.PublicImageMapper;
import com.project.patterndesignserver.model.content.image.PublicImage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublicImageDBTest {
    @Autowired
    PublicImageMapper publicImageMapper;

    @Test
    public void add1(){
        PublicImage publicImage = new PublicImage();
        publicImage.setImageName("testpic3");
        publicImage.setAvailable(true);
        publicImage.setTag("bird");
        publicImage.setPermission("all");
        publicImage.setMD5("adasdasd");
        publicImage.setImagePath("/home/desktop/");
        publicImage.setThumbnailPath("/home/desktop/1");
        publicImageMapper.addImage(publicImage);
    }

    @Test
    public void select1(){
        PublicImage publicImage =publicImageMapper.selectImageById(3);
        System.out.println(publicImage.getTag());
        System.out.println(publicImage.toString());
        List<String> t = publicImage.getTags();
//        for(String s: publicImage.getTags()){
////        for(String s : "animals|trees|bird".split("\\|")){
//            System.out.println(s);
//        }
        t.add("dragon");
        publicImage.setTagByTagsList(t);
//        for(String s: publicImage.getTags()){
//            System.out.println(s);
//        }
        System.out.println(publicImage.getTag());

    }

    @Test
    public void delete1(){
        publicImageMapper.deleteImage(1);
    }

    @Test
    public void queryTag(){
        List<PublicImage> publicImages =publicImageMapper.queryByTag("bird");
        for(PublicImage image : publicImages){
            System.out.println(image.toString());
        }

    }

    @Test
    public void queryTags(){
        List<String> tags = new ArrayList<>();
        tags.add("bird");
        tags.add("trees");
        List<PublicImage> publicImages =publicImageMapper.queryByTagsAnd(tags);
        for(PublicImage image : publicImages){
            System.out.println(image.toString());
        }
    }

    @Test
    public void queryTags2(){
        List<String> tags = new ArrayList<>();
        tags.add("bird");
        tags.add("trees");
        List<PublicImage> publicImages =publicImageMapper.queryByTagsOr(tags);
        for(PublicImage image : publicImages){
            System.out.println(image.toString());
        }
    }
    @Test
    public void queryByP(){
        List<PublicImage> publicImages =publicImageMapper.queryByPermission("ALL");
        for(PublicImage image : publicImages){
            System.out.println(image.toString());
        }
    }
}
