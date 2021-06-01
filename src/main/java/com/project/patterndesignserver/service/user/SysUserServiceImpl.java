package com.project.patterndesignserver.service.user;

import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.util.TimeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public String addUser(User user){
        try{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setCreateTime(TimeUtil.getCurrentTime());
            user.setLastModify(TimeUtil.getCurrentTime());
            userMapper.addUser(user);
            return "add succeeded";

        }catch (Exception e){
            e.printStackTrace();
            return "add failed";
        }
    }
    @Override
    public String deleteUser(long id){
        try{
            userMapper.deleteRoleRelation(id);
            userMapper.deleteUser(id);
            return "delete succeeded";
        }catch (Exception e){
            e.printStackTrace();
            return "delete failed";
        }
    }
    @Override
    public String updateUser(long id,String password,String email,
                             String mobile,String username,Boolean enabled){
        try{
            User user = userMapper.selectUserById(id);
            if(password!=null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(password));
            }
            if(email!=null) user.setEmail(email);
            if(mobile!=null) user.setMobile(mobile);
            if(username!=null) user.setUsername(username);
            if(enabled !=null ) user.setEnabled(enabled);
            userMapper.updateUser(user);
            return "update succeeded";
        }catch (Exception e){
            e.printStackTrace();
            return "update failed";
        }
    }

    @Override
    public List<User> queryAll(){
        try{
            return userMapper.selectAllUser();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
