package com.project.patterndesignserver.mapper.member;

import com.project.patterndesignserver.model.member.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user where id =#{id}")
    User getUserByID(@Param("id") long id);

    @Insert("INSERT INTO user(id,username,email,password,mobile,enabled,createTime,lastModify) " +
            "VALUES(#{id},#{username},#{email},#{password},#{mobile},#{enabled},#{createTime},#{lastModify})")
    int add(User user);

    @Delete("Delete FROM user where id = #{id}")
    int deleteByID(@Param("id") long id);

    @Update("Update user set password=#{password} where id = #{id}")
    int updatePasswordByID(@Param("id") long id, @Param("password") String password);

    User selectUserById(Integer id);

    List<User> selectAllUser();
}
