package com.xiangxue.ch5.bq;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：订单的实体类
 */
public class Order {
	private final String orderNo;//订单的编号
	private final double orderMoney;//订单的金额
	
	public Order(String orderNo, double orderMoney) {
		super();
		this.orderNo = orderNo;
		this.orderMoney = orderMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public double getOrderMoney() {
		return orderMoney;
	}
	
	
	
}
