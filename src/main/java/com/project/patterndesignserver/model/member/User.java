package com.project.patterndesignserver.model.member;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class User implements UserDetails {

    //用户登陆的核心信息
    private long id;  //用户ID 唯一属性 主键
    private String username;
    private String email;
    private String mobile;
    private String password;

    //用户状态
    private Boolean enabled;

    //用户角色权限相关
    /*
      一个用户可以有好几种角色，比如一个网站管理员，可以是管理员也同时是用户
      一个用户可以是高级的vip拥有高级权限，但也可以同时是低级用户，拥有低级用户的权限
     */
    private List<UserRole> roles;


    //初识信息
    private String createTime;
    private String lastModify;

    //实现接口
    @Override
    public String getUsername(){ return this.username;}
    @Override
    public boolean isAccountNonExpired(){ return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}
    @Override
    public boolean isCredentialsNonExpired(){ return true;}
    @Override
    public boolean isEnabled(){ return this.enabled;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<UserRole> roles = this.getRoles();
        System.out.println("finding roles: "+roles);
        for(UserRole role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

}
