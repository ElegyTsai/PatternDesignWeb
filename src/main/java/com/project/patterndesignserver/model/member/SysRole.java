package com.project.patterndesignserver.model.member;

import lombok.Data;

@Data
public class SysRole {
    private long id;
    private String name;
    private long roleper_id;
    private long permission_id;
}
