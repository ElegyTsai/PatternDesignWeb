package com.project.patterndesignserver.controller.sysuser;

import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.mapper.member.UserRoleMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserPermission;
import com.project.patterndesignserver.service.user.SysUserService;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/user")
public class SysUserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addUser(User user){
        return sysUserService.addUser(user);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@Param("id") long id){
        return sysUserService.deleteUser(id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public String updateUser(@Param("id") long id, @Param("password") String password, @Param("email") String email,
                             @Param("mobile") String mobile,@Param("username")String username,@Param("enable") Boolean enabled){
        return sysUserService.updateUser(id,password,email,mobile,username,enabled);
    }

    @GetMapping("/queryAll")
    @ResponseBody
    public List<User> queryAll(){
        return sysUserService.queryAll();
    }

    @GetMapping("/whoIam")
    @ResponseBody
    public String whoIam(){
        String username =SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = (User) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("user_"+username)),User.class);
//        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  user.getId()+"";
    }
}
