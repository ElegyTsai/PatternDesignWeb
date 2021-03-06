package com.project.patterndesignserver.module.filter;

import com.project.patterndesignserver.mapper.sys.UserLoginLogMapper;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.model.sys.UserLoginLog;
import com.project.patterndesignserver.util.IpUtil;
import com.project.patterndesignserver.util.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    private final UserLoginLogMapper userLoginLogMapper;

    private final StringRedisTemplate stringRedisTemplate;


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, StringRedisTemplate stringRedisTemplate, UserLoginLogMapper userLoginLogMapper){
        super(authenticationManager);
        this.userLoginLogMapper=userLoginLogMapper;
        this.stringRedisTemplate=stringRedisTemplate;

    }
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
        stringRedisTemplate = null;
        userLoginLogMapper = null ;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException{
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);

        if(tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)){
            //System.out.println("miss token");
            chain.doFilter(request,response);
            return;
            //????????????????????????
        }

        String tokenValue = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX,"");
        UsernamePasswordAuthenticationToken authentication = null;
        String uname;
        String loginInfo;
        int state;
        try{
            uname=JwtTokenUtil.getUsername(tokenValue);
            if(JwtTokenUtil.isExpiration(tokenValue)){
                System.out.println("expired");
                throw new Exception();
            }
            authentication = getAuthentication(tokenHeader);
            state=1;
        }catch (Exception e){
            uname = "unknown";
            state=0;
        }

        UserLoginLog loginRecord = new UserLoginLog();
        loginRecord.setLoginip(IpUtil.getIpAddr(request));
        loginRecord.setLoginTime(System.currentTimeMillis());
        loginRecord.setStates(state);
        loginRecord.setUsername(uname);
        loginRecord.setWay(2); // jwt??????
//        System.out.println(loginRecord.toString());
        userLoginLogMapper.saveLog(loginRecord);
//        System.out.println(loginInfo);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.doFilterInternal(request,response,chain);

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("header:" +SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//        System.out.println("header:"+SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
        try {
            String token = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
            String username = JwtTokenUtil.getUsername(token);
            List<SimpleGrantedAuthority> role = JwtTokenUtil.getUserRole(token);
            if (username != null) {
                //User user = (User) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("user_"+username)),User.class);
                return new UsernamePasswordAuthenticationToken(username, null,
                        role);
            }
            return null;
        }catch (Exception e){
//            e.printStackTrace();
            return null;
        }
    }
}
