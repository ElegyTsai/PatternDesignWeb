package com.project.patterndesignserver.model.member;

import lombok.Data;

import java.util.List;

@Data
public class UserRole {
    //角色表的主键
    private long id;
    private String roleName;

    private String description;
    private Boolean available = Boolean.FALSE;

    private List<UserPermission> permissions;
}
