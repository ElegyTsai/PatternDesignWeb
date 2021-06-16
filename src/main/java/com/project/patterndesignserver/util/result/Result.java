package com.project.patterndesignserver.util.result;

import lombok.Data;

@Data
public class Result <T>{
    private String code;
    private String msg;
    private T data;

    public Result (){
        ExceptionMsg msg1 = ExceptionMsg.SUCCESS;
        this.code = msg1.getCode();
        this.msg  = msg1.getMsg();
    }
    public Result (ExceptionMsg msg1){
        this.code = msg1.getCode();
        this.msg  = msg1.getMsg();
    }

}
