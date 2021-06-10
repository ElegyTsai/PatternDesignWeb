package com.project.patterndesignserver.util.result;

public enum ExceptionMsg {
    FAIL("4000","操作失败"),
    SUCCESS("1000","操作成功"),
    UserNotFound("4001","用户没有找到"),
    UsernameNotFound("4002","用户名没有找到"),
    UsernameUsed("4003","用户名已被使用"),
    PhoneUsed("4004","手机号已被使用"),
    CodeError("4005","验证码错误或过期"),
    EmailUsed("4010","邮箱已被使用"),
    EmailError("4011","邮箱错误"),
    TimeOut("4012","签名过期"),
    KeyWrong("4013","签名错误"),
    LackInfo("4020","登陆信息缺失")
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
