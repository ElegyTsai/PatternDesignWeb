package com.project.patterndesignserver.member;

import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.mapper.member.UserPermissionMapper;
import com.project.patterndesignserver.mapper.member.UserRoleMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserPermission;
import com.project.patterndesignserver.model.member.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DataBaseInitTest {
    @Autowired
    UserPermissionMapper userPermissionMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    public void PermissionInitTest1(){
        UserPermission userPermission1= new UserPermission();
        userPermission1.setUrl("/user/**");
        userPermission1.setPermission("add|query");
        userPermission1.setName("userP1");
        userPermission1.setAvailable(true);
        userPermissionMapper.addPermission(userPermission1);
    }

    @Test
    public void PermissionInitTest2(){
        UserPermission userPermission2= new UserPermission();
        userPermission2.setUrl("/user/**");
        userPermission2.setPermission("update");
        userPermission2.setName("userP2");
        userPermission2.setAvailable(true);
        userPermissionMapper.addPermission(userPermission2);
    }
    @Test
    public void PermissionInitTest3(){
        UserPermission userPermission3= new UserPermission();
        userPermission3.setUrl("/user/**");
        userPermission3.setPermission("delete");
        userPermission3.setName("userP3");
        userPermission3.setAvailable(true);
        userPermissionMapper.addPermission(userPermission3);
    }

    @Test
    public void UserRoleInitTest1(){
        UserRole userRole1 = new UserRole();
        userRole1.setRoleName("guest");
        userRole1.setDescription("No need to log in");
        userRole1.setAvailable(true);
        List<UserPermission> permissions = new ArrayList<>();
        permissions.add(new UserPermission(1));
        userRole1.setPermissions(permissions);
        userRoleMapper.addUserRole(userRole1);
        userRoleMapper.savePermissions(userRole1);
    }
    @Test
    public void UserRoleInitTest2(){
        UserRole userRole1 = new UserRole();
        userRole1.setRoleName("common");
        userRole1.setDescription("common user");
        userRole1.setAvailable(true);
        List<UserPermission> permissions = new ArrayList<>();
        permissions.add(new UserPermission(1));
        permissions.add(new UserPermission(2));
        userRole1.setPermissions(permissions);
        userRoleMapper.addUserRole(userRole1);
        userRoleMapper.savePermissions(userRole1);
    }

    @Test
    public void UserRoleInitTest3(){
        UserRole userRole1 = new UserRole();
        userRole1.setRoleName("admin");
        userRole1.setDescription("permit all");
        userRole1.setAvailable(true);
        List<UserPermission> permissions = new ArrayList<>();
        permissions.add(new UserPermission(1));
        permissions.add(new UserPermission(2));
        permissions.add(new UserPermission(3));
        userRole1.setPermissions(permissions);
        userRoleMapper.addUserRole(userRole1);
        userRoleMapper.savePermissions(userRole1);
    }

    @Test
    public void UserInitTest1(){

        User user1 = new User();
//        user1.setCreateTime("now");
        user1.setEmail("1234@qq.com");
        user1.setMobile("11011");
        user1.setPassword("password124577");
        user1.setUsername("caiwei1");
//        user1.setLastModify("now");
        List<UserRole> roles = new ArrayList<>();
        roles.add(new UserRole(3));
        user1.setRoles(roles);
        userMapper.addUser(user1);
        userMapper.saveRole(user1);
    }

    @Test
    public void UserInitTest2(){

        User user1 = new User();
//        user1.setCreateTime("now2");
        user1.setEmail("12345@qq.com");
        user1.setMobile("11111");
        user1.setPassword("password577");
        user1.setUsername("caiwei_admin");
//        user1.setLastModify("now2");
        List<UserRole> roles = new ArrayList<>();
        roles.add(new UserRole(3));
        roles.add(new UserRole(5));
        roles.add(new UserRole(4));
        user1.setRoles(roles);
        userMapper.addUser(user1);
        userMapper.saveRole(user1);
    }
    @Test
    public void UserDeleteTest2(){

//        User user1 = userMapper.selectUserByEmail("1234@qq.com");
        userMapper.deleteRoleRelation(3);
        userMapper.deleteUser(3);

    }
}
