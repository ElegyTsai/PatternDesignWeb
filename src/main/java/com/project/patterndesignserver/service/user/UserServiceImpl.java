package com.project.patterndesignserver.service.user;


import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserRole;
import com.project.patterndesignserver.util.MD5Util;
import com.project.patterndesignserver.util.TimeUtil;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends BaseController implements UserService{
    @Autowired
    UserMapper userMapper;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Response registerByEmail(User user){
        System.out.println(user.getUsername());
        try{

            User register = userMapper.selectUserByEmail(user.getEmail());
            if(register!=null){
                return result(ExceptionMsg.EmailUsed);
            }
            else{
                register = userMapper.selectUserByUsername(user.getUsername());
                if(register!=null){
                    return result(ExceptionMsg.UsernameUsed);
                }
                //设定账户基本信息
                user.setCreateTime(TimeUtil.getCurrentTime());
                user.setLastModify(TimeUtil.getCurrentTime());
                user.setActive(false);
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(user.getPassword()));
                List<UserRole> roles = new ArrayList<>();
                roles.add(new UserRole("ROLE_USER"));
                user.setActive(false);
                //开始保存账户
                user.setRoles(roles);
                userMapper.addUser(user);
                userMapper.saveRole(user);
                rabbitTemplate.convertAndSend("reg.email",user.getEmail());
            }
        }
        catch (Exception e){
            return result(ExceptionMsg.FAIL);
        }
        return result();
    }

    @Override
    public Response activeUserByEmail(String sid,String email){
        try{
            User user = userMapper.selectUserByEmail(email);

            if(user == null){
                return result(ExceptionMsg.UsernameNotFound );
            }
//            System.out.println(email);
//            System.out.println(user.getId());

            Timestamp outDate = Timestamp.valueOf(user.getOutDate());
            if(outDate.getTime()<=System.currentTimeMillis()){
                //如果已经过期了的话，直接把这个账户删除了
                userMapper.deleteRoleRelation(user.getId());
                userMapper.deleteUser(user.getId());
                return result(ExceptionMsg.TimeOut);
            }
            String key = user.getEmail()+"$"+outDate.getTime()/1000*1000 +"$"+user.getValidationCode();
            String digitalSignature = MD5Util.encode(key);
            if(!digitalSignature.equals(sid)){
                return result(ExceptionMsg.KeyWrong);
            }
            userMapper.setActiveByEmail(true,email);
            userMapper.clearOutDateAndValidationCode(user.getId());
            System.out.println(email+": 邮箱验证成功");
            return result(ExceptionMsg.SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.FAIL);
        }
    }

    @Override
    public Response updatePassword(User user){
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            //加密密码
            userMapper.setPassword(user);
        }
        catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.FAIL);
        }
        return result();
    }
    @Override
    public Response login(User user){
        try{
            if(user.getPassword()==null){
                return result(ExceptionMsg.LackInfo);
            }
            if(user.getEmail()==null){
                if(user.getMobile()==null){
                    return result(ExceptionMsg.LackInfo);
                }else{
                    userMapper.selectUserByMobile(user.getMobile());
                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.FAIL);
        }
        return result();
    }

    @Override
    public Response logout(){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            stringRedisTemplate.delete(name);
            return result(ExceptionMsg.SUCCESS);
        }
        catch (Exception e)
        {
            return result(ExceptionMsg.FAIL);
        }
    }

}
