package com.project.patterndesignserver.util.result;

public enum ExceptionMsg {
    FAIL("4000","操作失败"),
    SUCCESS("1000","操作成功"),
    UserNotFound("4001","用户没有找到"),
    UsernameNotFound("4002","用户名没有找到"),
    UsernameUsed("4003","用户名已被使用"),
    EmailUsed("4010","邮箱已被使用"),
    EmailError("4011","邮箱错误"),
    TimeOut("4012","签名过期"),
    KeyWrong("4113","签名错误")
    ;
    private String code;
    private String msg;

    ExceptionMsg(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}