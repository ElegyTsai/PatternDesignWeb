package com.project.patterndesignserver.mapper.member;

import com.project.patterndesignserver.model.member.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user where id =#{id}")
    User getUserByID(@Param("id") long id);

    @Insert("INSERT INTO user(id,username,email,password,mobile,enabled,createTime,lastModify,role) " +
            "VALUES(#{id},#{username},#{email},#{password},#{mobile},#{enabled},#{createTime},#{lastModify},#{roles})")
    int add(User user);

    @Delete("Delete FROM user where id = #{id}")
    int deleteByID(@Param("id") long id);

    @Update("Update user set password=#{password} where id = #{id}")
    int updatePasswordByID(@Param("id") long id, @Param("password") String password);

}
