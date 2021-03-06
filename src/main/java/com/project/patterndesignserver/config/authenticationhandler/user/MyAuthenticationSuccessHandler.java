package com.project.patterndesignserver.config.authenticationhandler.user;

import com.google.gson.JsonObject;
import com.project.patterndesignserver.mapper.sys.UserLoginLogMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.sys.UserLoginLog;
import com.project.patterndesignserver.util.IpUtil;
import com.project.patterndesignserver.util.JwtTokenUtil;
import com.project.patterndesignserver.util.result.Response;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    UserLoginLogMapper userLoginLogMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException,SecurityException{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Authentication success");
        if(principal !=null && principal instanceof UserDetails){
            User user = (User)principal;
            UserLoginLog loginRecord = new UserLoginLog();
            loginRecord.setLoginip(IpUtil.getIpAddr(request));
            loginRecord.setLoginTime(System.currentTimeMillis());
            loginRecord.setStates(1);
            loginRecord.setUsername(request.getParameter("uname")+"");
            loginRecord.setWay(1); // web??????
            userLoginLogMapper.saveLog(loginRecord);

            boolean isRemember;
            if(request.getParameter("isRememberMe")!=null &&request.getParameter("isRememberMe").equals("true")){
                isRemember = true;
//                System.out.println("isRemember");
            }
            else isRemember = false;
            String token = JwtTokenUtil.crateToken(user.getId()+"",user.roleToString(),isRemember);
//            stringRedisTemplate.opsForValue().set(user.getId()+"",token,isRemember?7:1, TimeUnit.DAYS);
            stringRedisTemplate.opsForValue().set("user_"+user.getId(),JSONObject.fromObject(user).toString());
            System.out.println("key:"+user.getMobile());
            System.out.println("value:"+token);
//            String preToken = stringRedisTemplate.opsForValue().get(JwtTokenUtil.getUsername(token));
//            System.out.println("getValue:"+preToken);
            response.addHeader(JwtTokenUtil.TOKEN_HEADER,JwtTokenUtil.TOKEN_PREFIX+token);
            response.setContentType("application/json;charset=utf-8");

            Response rmsg = new Response();
            PrintWriter out = response.getWriter();
            JsonObject JObject = new JsonObject();
            JObject.addProperty("rspCode",rmsg.getRspCode());
            JObject.addProperty("rspMsg",rmsg.getRspMsg());
            JObject.addProperty("username",user.getUsername());
            out.write(JObject.toString());
            out.flush();
            out.close();
        }
    }
}
