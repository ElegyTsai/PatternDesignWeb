package com.project.patterndesignserver.config.authenticationhandler.user;

import com.project.patterndesignserver.mapper.sys.UserLoginLogMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.sys.UserLoginLog;
import com.project.patterndesignserver.util.IpUtil;
import com.project.patterndesignserver.util.JwtTokenUtil;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Autowired
        UserLoginLogMapper userLoginLogMapper;
        @Autowired
        StringRedisTemplate stringRedisTemplate;

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException e) throws IOException,SecurityException{
            request.setCharacterEncoding("UTF-8");
            String username = request.getParameter("mobile");
            System.out.println("Authentication fail");
            UserLoginLog loginRecord = new UserLoginLog();
            loginRecord.setLoginip(IpUtil.getIpAddr(request));
            loginRecord.setLoginTime(System.currentTimeMillis());
            loginRecord.setStates(0);
            loginRecord.setUsername(username);
            loginRecord.setWay(1); // web登陆
            userLoginLogMapper.saveLog(loginRecord);
            Response rmsg ;
            System.out.println(e.getMessage());
            if(e instanceof UsernameNotFoundException){
                rmsg = new Response(ExceptionMsg.UserNotExisted);
            }else if (e instanceof LockedException) {
                rmsg = new Response(ExceptionMsg.UserDiabled);
            }
            else{
                    rmsg = new Response(ExceptionMsg.PasswordError);
            }

            JsonObject JObject = new JsonObject();
            JObject.addProperty("rspCode",rmsg.getRspCode());
            JObject.addProperty("rspMsg",rmsg.getRspMsg());

            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(JObject.toString());
            out.flush();
            out.close();
        }

}
