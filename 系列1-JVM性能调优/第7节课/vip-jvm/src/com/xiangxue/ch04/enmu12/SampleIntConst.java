package com.xiangxue.ch04.enmu12;

public class SampleIntConst {
	
	//延时订单状态
	public static final int ORDER_DEPOT_UNPAY = 0;
	//public static final String ORDER_DEPOT_UNPAY_DESC = "unpay";
	public static final int ORDER_DEPOT_PAID = 3;
	public static final int ORDER_DEPOT_TIMOUT = 2;
	
	//物流订单状态
	public static final int ORDER_LOGISTICS_READY = 0;
	public static final int ORDER_LOGISTICS_TRANSPORT = 1;
	public static final int ORDER_LOGISTICS_ARRIVED = 2;
	
	//
	public enum Depot{UNPAY,PAID,TIMOUT}//0,1,2
	public enum Logistics{READY,TRANSPORT,ARRIVED}
	
	public static void main(String[] args) {
		
		System.out.println(Depot.UNPAY);
		System.out.println(Depot.UNPAY.ordinal());
//		if(==0)
//			switch status
//			case 0:
	
	}

	
	
}
