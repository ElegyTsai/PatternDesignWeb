package com.project.patterndesignserver.mapper.content.image;

import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.model.content.image.UserImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserImageMapper {

    int addImage(UserImage userImage);

    int saveOwner(UserImage userImage);

    UserImage queryByMD5(String MD5);

    UserImage selectPic(String UUID,long userId);

    List<UserImage> queryByUserId(long id);

    int deleteRelation(String UUID,long userId);

    int deleteAllRelation(long userId);
}
