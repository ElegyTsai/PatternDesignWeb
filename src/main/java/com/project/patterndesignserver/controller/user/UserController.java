package com.project.patterndesignserver.controller.user;

import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.mapper.member.UserRoleMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserRole;
import com.project.patterndesignserver.service.user.UserService;
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

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        return result().toString();
    }

    @ResponseBody
    @RequestMapping(value = "/register/email", method = RequestMethod.POST)
    public Response registerByEmail(User user){
        return userService.registerByEmail(user);
    }

    @ResponseBody
    @RequestMapping(value = "/activeuser/email", method = RequestMethod.GET)
    public Response activateUser(@Param("sid")String sid,@Param("email") String email){
        return userService.activeUserByEmail(sid, email);
    }

    @ResponseBody
    @RequestMapping(value = "/user/password",method = RequestMethod.POST)
    public Response setPassword(User user){
        return userService.updatePassword(user);
    }

    @ResponseBody
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Response Login(User user){
        return result();
    }

}
