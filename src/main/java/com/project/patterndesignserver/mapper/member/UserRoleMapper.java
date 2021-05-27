package com.project.patterndesignserver.mapper.member;

import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserPermission;
import com.project.patterndesignserver.model.member.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserRoleMapper {

    UserRole selectUserRoleById(long id);

    List<User> queryUserByRoleId(long roleId);

    List<UserPermission> queryPermissionByRoleId(long roleId);

    int addUserRole(UserRole userRole);

    int updateUserRole(UserRole userRole);

    int deleteUserRole(long id);

}
