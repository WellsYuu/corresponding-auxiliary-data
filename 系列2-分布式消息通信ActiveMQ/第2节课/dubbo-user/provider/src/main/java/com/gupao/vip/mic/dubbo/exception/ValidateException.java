package com.gupao.vip.mic.dubbo.exception;

import com.gupao.vip.mic.dubbo.user.constants.ResponseCodeEnum;

/**
 * Create by qingyin@wacai.com
 */
public class ValidateException extends RuntimeException {

    /**
     * versionId
     */
    private static final long serialVersionUID = 7172827201346602909L;


    /**
     * 返回码
     */
    private String errorCode;
    /**
     * 信息
     */
    private String errorMessage;

    /**
     * 构造函数
     */
    public ValidateException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param errorCode
     */
    public ValidateException(String errorCode) {
        super(errorCode);
        this.errorCode= ResponseCodeEnum.SYS_PARAM_NOT_RIGHT.getCode();
        this.errorMessage=ResponseCodeEnum.STATUS_NOT_RIGHT.getMsg(errorCode);
    }

    /**
     * 构造函数
     *
     * @param cause
     */
    public ValidateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     *
     * @param errorCode
     * @param cause
     */
    public ValidateException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数
     *
     * @param errorCode
     * @param message
     */
    public ValidateException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * 构造函数
     *
     * @param errorCode
     * @param message
     * @param cause
     */
    public ValidateException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     *
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorMessage</tt>.
     *
     * @return property value of errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter method for property <tt>errorMessage</tt>.
     *
     * @param errorMessage value to be assigned to property errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
