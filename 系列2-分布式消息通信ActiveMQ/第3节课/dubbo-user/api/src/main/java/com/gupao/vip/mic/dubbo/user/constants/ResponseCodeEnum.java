package com.gupao.vip.mic.dubbo.user.constants;

public enum ResponseCodeEnum {

    /**
     * 公共系统返回码 （第四位是0）
     */
    SYS_SUCCESS("000000", "成功"),
    SYS_PARAM_NOT_RIGHT("001001", "请求数据校验失败"),
    QUERY_DATA_NOT_EXIST("001002", "查询数据不存在"),
    STATUS_NOT_RIGHT("001003", "数据状态校验不通过"),
    REQUEST_DATA_NOT_EXIST("001004", "请求提交的数据不存在"),
    USER_NOT_LOGIN("001006","用户未登录"),
    USER_OR_PASSWORD_ERROR("001007","用户不存在或帐号密码错误"),
    ACCESS_LIMITER("001008","访问被限制"),
    DATA_SAVE_ERROR("001009","数据保存失败"),
    SYSTEM_BUSY("001099", "系统繁忙,请稍后重试");


    private final String code;
    private final String msg;

    ResponseCodeEnum(String code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getCode(){return code;}
    public String getMsg(){return msg;}

    public String getMsg(String detailDesc){return msg+" : "+detailDesc;}

}
