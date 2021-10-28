package com.xiangxue.ch04.enmu12;
/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：加班费计算,工作日加班2倍，节假日3倍
 */
public enum BetterPayDay {
	MONDAY(PayType.WORK), TUESDAY(PayType.WORK), WEDNESDAY(
			PayType.WORK), THURSDAY(PayType.WORK), FRIDAY(PayType.WORK), 
	SATURDAY(PayType.REST), SUNDAY(PayType.REST),WUYI(PayType.REST);

	private final PayType payType;//成员变量

	BetterPayDay(PayType payType) {
		this.payType = payType;
	}

	double pay(double hoursOvertime) {
		return payType.pay(hoursOvertime);
	}

	//策略枚举
	private enum PayType {
		WORK {
			double pay(double hoursOvertime) {
				return hoursOvertime*HOURS_WORK;
			}
		},
		REST {
			double pay(double hoursOvertime) {
				return hoursOvertime*HOURS_REST;
			}
		};
		
		private static final int HOURS_WORK = 2;
		private static final int HOURS_REST = 3;

		abstract double pay(double hoursOvertime);//抽象计算加班费的方法
	}
	
	public static void main(String[] args) {
		System.out.println(BetterPayDay.MONDAY.pay(7.5));
	}
}
