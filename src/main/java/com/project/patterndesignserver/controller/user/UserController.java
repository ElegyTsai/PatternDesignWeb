package com.project.patterndesignserver.controller.user;

import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.mapper.member.UserRoleMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserRole;
import com.project.patterndesignserver.util.TimeUtil;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

                user.setCreateTime(TimeUtil.getCurrentTime());
                user.setLastModify(TimeUtil.getCurrentTime());
                user.setActive(false);
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(user.getPassword()));
                List<UserRole> roles = new ArrayList<>();
                roles.add(new UserRole("ROLE_USER"));
                user.setRoles(roles);
                userMapper.addUser(user);
                userMapper.saveRole(user);
                /*
                缺少对密码进行加密保护
                 */
                rabbitTemplate.convertAndSend("reg.email",user.getEmail());
            }
        }
        catch (Exception e){
            return result(ExceptionMsg.FAIL);
        }
        return result();

    }

}
