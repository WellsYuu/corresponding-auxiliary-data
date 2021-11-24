package com.gupao.vip.mic.dubbo.exception;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServiceException extends RuntimeException{

    /**返回码*/
    private String errorCode;
    /**信息*/
    private String errorMessage;

    /**
     * 构造函数
     */
    public ServiceException() {
        super();
    }

    /**
     * 构造函数
     * @param errorCode
     */
    public ServiceException(String errorCode) {
        super(errorCode);
    }

    /**
     * 构造函数
     * @param cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param errorCode
     * @param cause
     */
    public ServiceException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数
     * @param errorCode
     * @param message
     */
    public ServiceException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * 构造函数
     * @param errorCode
     * @param message
     * @param cause
     */
    public ServiceException(String errorCode, String message, Throwable cause) {
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

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ServiceException [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
    }
}
