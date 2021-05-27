package com.project.patterndesignserver.model.member;

import lombok.Data;

@Data
public class SysUser {
    private long id;
    private long user_id;
    private long role_id;
    private boolean enabled;
    private String sysusername;
}
