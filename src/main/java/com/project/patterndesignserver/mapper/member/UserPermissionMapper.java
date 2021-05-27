package com.project.patterndesignserver.mapper.member;

import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserPermissionMapper {
    UserPermission selectPermissionById(long id);

    int addPermission(UserPermission userPermission);

    int deletePermissionById(long id);

    int updatePermission(UserPermission userPermission);

}
