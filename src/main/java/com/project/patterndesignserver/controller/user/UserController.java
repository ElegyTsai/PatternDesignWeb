package com.project.patterndesignserver.controller.user;

import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.mapper.member.UserRoleMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserRole;
import com.project.patterndesignserver.util.MD5Util;
import com.project.patterndesignserver.util.TimeUtil;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("home")
public class UserController extends BaseController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @ResponseBody
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        return result().toString();
    }

    @ResponseBody
    @RequestMapping(value = "/register/email", method = RequestMethod.POST)
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

    @ResponseBody
    @RequestMapping(value = "/activeuser/email", method = RequestMethod.GET)
    Response activateUser(@Param("sid")String sid,@Param("email") String email){
        try{
            User user = userMapper.selectUserByEmail(email);
            System.out.println(email);
            System.out.println(user.getId());
            if(user == null){
                return result(ExceptionMsg.UsernameNotFound );
            }
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

}
