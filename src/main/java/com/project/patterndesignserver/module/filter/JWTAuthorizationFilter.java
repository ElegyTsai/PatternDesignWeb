package com.project.patterndesignserver.module.filter;

import com.project.patterndesignserver.util.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
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

import java.util.Collections;
import java.util.List;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException{
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if(tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
            //表示直接放行通过
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request,response,chain);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("header:" +SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        System.out.println("header:"+SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
        String token = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX,"");
        String username = JwtTokenUtil.getUsername(token);
        List<SimpleGrantedAuthority> role = JwtTokenUtil.getUserRole(token);
        if( username != null){
            return new UsernamePasswordAuthenticationToken(username,null,
                    role);
        }
        return null;
    }
}
