package com.project.patterndesignserver.util.result;

public enum ExceptionMsg {
    FAIL("4000","操作失败"),
    SUCCESS("2000","操作成功"),
    UserNotFound("4001","用户没有找到"),
    UsernameNotFound("4002","用户名没有找到"),
    UsernameUsed("4003","用户名已被使用"),
    PhoneUsed("4004","手机号已被使用"),
    CodeError("4005","验证码错误或过期"),
    UserNotAuthenticated("4006","用户未登陆"),
    EmailUsed("4010","邮箱已被使用"),
    EmailError("4011","邮箱错误"),
    TimeOut("4012","签名过期"),
    PasswordError("4013","密码错误"),
    UserNotExisted("4014","账户未注册"),
    UserDisabled("4015","用户被锁定"),
    KeyWrong("4013","签名错误"),
    LackInfo("4020","登陆信息缺失"),
    UploadFailed("4101","上传失败"),
    SuffixError("4102","文件格式错误"),
    ServerBusy("4103","服务器繁忙，稍后重试"),
    OutdatedSession("4104","页面已经过期，请重新开始"),
    UndoError("4105","撤销失败")
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
