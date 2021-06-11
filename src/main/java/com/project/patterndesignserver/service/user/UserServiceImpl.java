package com.project.patterndesignserver.service.user;


import com.project.patterndesignserver.controller.BaseController;
import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.member.UserRole;
import com.project.patterndesignserver.service.verify.AsynSendVerifyCodeMessageService;
import com.project.patterndesignserver.util.JwtTokenUtil;
import com.project.patterndesignserver.util.MD5Util;
import com.project.patterndesignserver.util.TimeUtil;
import com.project.patterndesignserver.util.UUIDUtil;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends BaseController implements UserService{
    @Autowired
    UserMapper userMapper;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AsynSendVerifyCodeMessageService asynSendVerifyCodeMessageService;

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

    @Override
    public Response sendPhoneMessage(String phoneNumber) {
        try {
//            User user = userMapper.selectUserByMobile(phoneNumber);
//            if(user!=null){
//                return result(ExceptionMsg.PhoneUsed);
//            }
//            注释掉的原因是除了注册 还有其他地方也需要用到code  统一成这个api
            rabbitTemplate.convertAndSend("reg.phone",phoneNumber);
            return result();
        }
        catch (Exception e)
        {
            return result(ExceptionMsg.FAIL);
        }
    }

    @Override
    public Response registerByMobile(User user, HttpServletResponse response) {
//        System.out.println("注册用户："+user.getUsername());
        try{
        User userExisted = userMapper.selectUserByMobile(user.getMobile());
        if(userExisted!=null){
            //            return result(ExceptionMsg.PhoneUsed);
            //注释是为了方便调试
            userMapper.deleteRoleRelation(userExisted.getId());
            userMapper.deleteUser(userExisted.getId());

        }
        if(asynSendVerifyCodeMessageService.verifyCode(user.getMobile(),user.getValidationCode())){
            user.setCreateTime(TimeUtil.getCurrentTime());
            user.setLastModify(TimeUtil.getCurrentTime());
            user.setActive(true);
            user.setId(UUIDUtil.getUUIDOfNumber());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            List<UserRole> roles = new ArrayList<>();
            roles.add(new UserRole("ROLE_USER"));
            //开始保存账户
            user.setRoles(roles);
            userMapper.addUser(user);
            userMapper.saveRole(user);
            System.out.println("注册成功");
            //生成token 并缓存
            String token = JwtTokenUtil.crateToken(user.getId()+"",user.roleToString(),false);
            stringRedisTemplate.opsForValue().set(user.getId()+"",token,1, TimeUnit.DAYS);
            stringRedisTemplate.opsForValue().set("user_"+user.getId(), JSONObject.fromObject(user).toString());
            System.out.println("key:"+user.getMobile());
            System.out.println("value:"+token);
            String preToken = stringRedisTemplate.opsForValue().get(JwtTokenUtil.getUsername(token));
            System.out.println("getValue:"+preToken);
            response.addHeader(JwtTokenUtil.TOKEN_HEADER,JwtTokenUtil.TOKEN_PREFIX+token);
            response.setContentType("application/json;charset=utf-8");
            return result();
        }else{
            return result(ExceptionMsg.CodeError);
        }

        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.FAIL);
        }



    }

}
