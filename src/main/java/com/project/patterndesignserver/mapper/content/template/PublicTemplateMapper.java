package com.project.patterndesignserver.mapper.content.template;

import com.project.patterndesignserver.model.content.template.PublicTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublicTemplateMapper {
    List<PublicTemplate> queryByTag(String tag);

    int addTemplate(PublicTemplate template);
}
