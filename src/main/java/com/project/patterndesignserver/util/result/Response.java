package com.project.patterndesignserver.util.result;

public class Response {
    private String rspCode;
    private String rspMsg;

    public Response(ExceptionMsg msg){
        this.rspCode=msg.getCode();
        this.rspMsg=msg.getMsg();
    }
    public Response(){
        ExceptionMsg msg =  ExceptionMsg.SUCCESS;
        this.rspCode=msg.getCode();
        this.rspMsg=msg.getMsg();
    }

    @Override
    public String toString() {
        return "Response{" +
                "rspCode='" + rspCode + '\'' +
                ", rspMsg='" + rspMsg + '\'' +
                '}';
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }
}
