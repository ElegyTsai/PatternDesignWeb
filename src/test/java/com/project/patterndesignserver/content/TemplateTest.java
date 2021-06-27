package com.project.patterndesignserver.content;

import com.project.patterndesignserver.mapper.content.template.PublicTemplateMapper;
import com.project.patterndesignserver.model.content.template.PublicTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateTest {
    @Autowired
    PublicTemplateMapper publicTemplateMapper;

    @Test
    public void test(){
        PublicTemplate publicTemplate = new PublicTemplate();
        publicTemplate.setUrl("/123/123/123/4");
        publicTemplate.setThumbnailUrl("/thu/123123/123");
        publicTemplate.setTag("bird");
        publicTemplateMapper.addTemplate(publicTemplate);
    }
    @Test
    public void test2(){
        PublicTemplate publicTemplate = new PublicTemplate();
        publicTemplate.setUrl("/123/123/123/2");
        publicTemplate.setThumbnailUrl("/thu/123123/123/2");
        publicTemplate.setTag("bird");
        publicTemplateMapper.addTemplate(publicTemplate);
    }
    @Test
    public void test3(){
        List<PublicTemplate> templates = publicTemplateMapper.queryByTag("bird");

        return ;
    }

}
