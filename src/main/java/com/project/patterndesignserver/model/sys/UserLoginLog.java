package com.project.patterndesignserver.model.sys;

import lombok.Data;

@Data
public class UserLoginLog {
    private long id;
    private long loginTime;
    private String loginip;
    private String username;
    private int states;
    private int way;  // 1 表示web 2表示jwt

}
