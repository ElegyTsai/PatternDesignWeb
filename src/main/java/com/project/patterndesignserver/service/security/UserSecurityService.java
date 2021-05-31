package com.project.patterndesignserver.service.security;

import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.model.member.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{
        User user = userMapper.selectUserByUsername(name);
        if(user == null){
            User emailUser = userMapper.selectUserByEmail(name);
            if(emailUser == null){
                User mobileUser = userMapper.selectUserByMobile(name);
                if(mobileUser == null){
                    throw new UsernameNotFoundException("用户名邮箱手机不存在");
                }
                else{
                    user = mobileUser;
                }
            }
            else{
                user = emailUser;
            }
        }
        return user;
    }
}
