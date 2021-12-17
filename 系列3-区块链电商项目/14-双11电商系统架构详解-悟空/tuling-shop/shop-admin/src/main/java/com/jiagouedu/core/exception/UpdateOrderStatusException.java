package com.jiagouedu.core.exception;

/**
 * 修改订单状态异常
 * 
 * @author wukong 图灵学院 QQ:245553999ei
 * 
 */
public class UpdateOrderStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param string
	 */
	public UpdateOrderStatusException(String arg0) {
		super(arg0);
	}

}
