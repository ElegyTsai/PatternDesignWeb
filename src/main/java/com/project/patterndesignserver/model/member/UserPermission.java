package com.project.patterndesignserver.model.member;

import lombok.Data;

import java.util.Arrays;
import java.io.Serializable;
import java.util.List;

@Data
public class UserPermission implements Serializable {
    //primary key
    private Integer id;
    private String name;

    private String resourceType;
    private String url;
    private String permission;
    private Boolean available = Boolean.FALSE;
    private List permissions;
    private List<UserRole> roles;

    public List getPermissions(){
        return Arrays.asList(this.permission.trim().split("|"));
    }

    public void setPermissions(List permissions){
        this.permissions=permissions;
    }
}
