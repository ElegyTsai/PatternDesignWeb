package com.project.patterndesignserver.config.authenticationhandler.user;

import com.project.patterndesignserver.mapper.sys.UserLoginLogMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.sys.UserLoginLog;
import com.project.patterndesignserver.util.IpUtil;
import com.project.patterndesignserver.util.JwtTokenUtil;
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
            loginRecord.setUsername(user.getUsername());
            loginRecord.setWay(1); // web登陆
            userLoginLogMapper.saveLog(loginRecord);
            //需要一个跳转页面

            String token = JwtTokenUtil.crateToken(user.getUsername(),user.roleToString(),request.getParameter("isRememberMe").equals("true"));
            stringRedisTemplate.opsForValue().set(user.getUsername(),token);
            response.addHeader(JwtTokenUtil.TOKEN_HEADER,JwtTokenUtil.TOKEN_PREFIX+token);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("登陆成功");
            out.flush();
            out.close();
        }
    }
}
