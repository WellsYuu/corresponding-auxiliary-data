package com.gupao.framework.exception;

/**
 * <p>
 * MybatisPluginsException 异常类
 * </p>
 * 
 */
public class MybatisPluginsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MybatisPluginsException(String message) {
		super(message);
	}

	public MybatisPluginsException(Throwable throwable) {
		super(throwable);
	}

	public MybatisPluginsException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
