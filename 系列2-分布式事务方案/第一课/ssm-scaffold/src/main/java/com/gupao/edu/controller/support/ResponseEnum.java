package com.gupao.edu.controller.support;

public enum ResponseEnum {

    SUCCESS(0,"成功"),
    FAILED(1,"系统繁忙,请稍后重试"),
    ;
    private Integer code;
    private String msg;
    private ResponseEnum(Integer code,String msg){
        this.msg = msg;
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
