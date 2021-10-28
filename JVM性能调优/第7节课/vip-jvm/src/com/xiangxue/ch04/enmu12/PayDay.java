package com.xiangxue.ch04.enmu12;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：加班费计算,工作日加班2倍，节假日3倍
 */
enum PayDay {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,WUYI;
	
	private static final int HOURS_WORK = 2;
	private static final int HOURS_REST = 3;
	
	//超时时间
	double pay(double hoursOvertime) {
		switch(this) {
		case SATURDAY:case SUNDAY:
			return hoursOvertime*HOURS_REST;
		default:
			return hoursOvertime*HOURS_WORK;
		}
	}

	
}
