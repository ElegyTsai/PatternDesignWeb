package com.project.patterndesignserver.service.user;

import com.project.patterndesignserver.model.member.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserService {
    public String addUser(User user);

    public String deleteUser(long id);

    public String updateUser(long id,String password,String email,
                             String mobile,String username,Boolean enabled);

    public List<User> queryAll();
}
