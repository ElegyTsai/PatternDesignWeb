package com.project.patterndesignserver.mapper.member;

import com.project.patterndesignserver.model.member.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM user where id =#{id}")
//    User getUserByID(@Param("id") long id);
//
//    @Insert("INSERT INTO user(id,username,email,password,mobile,enabled,createTime,lastModify) " +
//            "VALUES(#{id},#{username},#{email},#{password},#{mobile},#{enabled},#{createTime},#{lastModify})")
//    int add(User user);
//
//    @Delete("Delete FROM user where id = #{id}")
//    int deleteByID(@Param("id") long id);
//
//    @Update("Update user set password=#{password} where id = #{id}")
//    int updatePasswordByID(@Param("id") long id, @Param("password") String password);

    User selectUserById(long id);

    User selectUserByEmail(String email);

    User selectUserByMobile(String mobile);

    User selectUserByUsername(String username);

    List<User> selectAllUser();

    int addUser(User user);

    int saveRole(User user);

    int updateUser(User user);

    int deleteRoleRelation(long id);

    int deleteUser(long id);

    int setValidationAndOutDateByEmail(String validationCode,String outDate,String email);

    int setActiveByEmail(boolean active,String email);

    int clearOutDateAndValidationCode(long id);




}
