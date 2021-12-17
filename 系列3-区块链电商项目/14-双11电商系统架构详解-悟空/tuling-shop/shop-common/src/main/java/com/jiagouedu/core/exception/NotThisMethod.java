package com.jiagouedu.core.exception;

/**
 * 不支持此操作异常
 * 
 * @author wukong 图灵学院 QQ:245553999ei
 * 
 */
public class NotThisMethod extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param string
	 */
	public NotThisMethod(String arg0) {
		super(arg0);
	}

}
