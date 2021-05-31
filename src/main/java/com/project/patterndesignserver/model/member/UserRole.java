package com.project.patterndesignserver.model.member;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRole implements Serializable {
    //角色表的主键
    private long id;
    private String roleName;
    private String description;
    private boolean available = true;
    private List<UserPermission> permissions;
    private List<User> users;

    public UserRole(){
    }
    public UserRole(long id){
        this.id=id;
    }
    public UserRole(String roleName){
        this.roleName=roleName;
    }


}
