package com.project.patterndesignserver.model.member;

import com.project.patterndesignserver.util.SerializableUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class User implements UserDetails {

    //用户登陆的核心信息
    private long id;  //用户ID 唯一属性 主键
    private String username;
    private String email;
    private String mobile;
    private String password;

    //用户状态
    private String enabled;

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
    public String getPassword(){return this.password;}
    @Override
    public boolean isAccountNonExpired(){ return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}
    @Override
    public boolean isCredentialsNonExpired(){ return true;}
    @Override
    public boolean isEnabled(){ return true;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<UserRole> roles = this.getMyRoles();
        System.out.println("finding roles: "+roles);
        for(UserRole role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() throws Exception{
        return SerializableUtil.serializeToString(this.roles);
    }
    public List<UserRole> getMyRoles(){
        return this.roles;
    }
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }
}
