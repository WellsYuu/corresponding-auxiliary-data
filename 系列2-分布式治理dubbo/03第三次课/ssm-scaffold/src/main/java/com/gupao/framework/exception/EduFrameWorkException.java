package com.gupao.framework.exception;

/**
 * <p>
 * EduFrameWorkException 异常类
 * </p>
 * 
 * @author qingyin
 * @Date 2016-08-19
 */
public class EduFrameWorkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EduFrameWorkException(String message) {
		super(message);
	}

	public EduFrameWorkException(Throwable throwable) {
		super(throwable);
	}

	public EduFrameWorkException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
