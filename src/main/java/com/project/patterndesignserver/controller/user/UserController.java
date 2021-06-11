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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("api/home")
public class UserController extends BaseController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    UserService userService;

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        return result().toString();
    }

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/register/email", method = RequestMethod.POST)
    public Response registerByEmail(User user){
        return userService.registerByEmail(user);
    }

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/activeuser/email", method = RequestMethod.GET)
    public Response activateUser(@Param("sid")String sid,@Param("email") String email){
        return userService.activeUserByEmail(sid, email);
    }
    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/user/password",method = RequestMethod.POST)
    public Response setPassword(User user){
        return userService.updatePassword(user);
    }
    @ApiIgnore
    @ResponseBody
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String Login(){
//        return result();
        return "login";
    }
    @ApiIgnore
    @ResponseBody
    @RequestMapping(value ="/logout",method = RequestMethod.GET)
    public Response logout(){
        return userService.logout();
    }


    @ApiOperation(value = "获取手机验证码",tags = {"注册","user-controller"},notes = "用get")
    @ResponseBody
    @RequestMapping(value = "/getCode",method = RequestMethod.GET)
    public Response getCode(@Param("mobile")String mobile){
        return userService.sendPhoneMessage(mobile);
    }


    @ResponseBody
    @RequestMapping(value = "/register/mobile",method = RequestMethod.POST)
    @ApiOperation(value = "手机用户注册",tags = {"注册","user-controller"},notes = "只能是POST,其他值保持空缺，" +
            "实际上只需要Mobile,username,password,validationCode这几个属性")
    public Response register(User user){
        return userService.registerByMobile(user);
    }
}
