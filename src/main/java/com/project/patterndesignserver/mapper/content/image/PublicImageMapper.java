package com.project.patterndesignserver.mapper.content.image;

import com.project.patterndesignserver.model.content.image.PublicImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublicImageMapper {

    PublicImage selectImageById(long id);

    int addImage(PublicImage publicImage);
    
    int deleteImage(long id);

    List<PublicImage> queryByTag (String tag);

    List<PublicImage> queryByTagsAnd(List<String> tags);

    List<PublicImage> queryByTagsOr(List<String> tags);

    List<PublicImage> queryByPermission(String permission);
}
