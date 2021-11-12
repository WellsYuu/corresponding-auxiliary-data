package com.xiangxue.ch04.varargs09;

import java.util.Collections;

import com.xiangxue.ch04.enmu12.SampleIntConst;

public class VarArags {
	
	//private User user ;
	
	public static void userOper(int count) {
		//SampleIntConst.ORDER_DEPOT_PAID;
		if (count<0) {
			throw new IllegalArgumentException("");
		}
	}
	
	//可能很多 0~很多
	static int sum(int... args) {
		int sum = 0;
		for (int arg : args)
			sum += arg;
		return sum;
	}
	
	//要求参数的个数，是1~多个
	//
//	static int sum1(int... args) {
//		if(args.length==0) {
//			//做点异常处理
//		}
//		if(args[0]==100) {
//			
//		}
//		for(int i=1;i<args.length;i++) {
//			int sum = 0;
//			sum += args[i];
//			return sum;	
//		}
//	}
//	
//	static int sum2(int flag, int... args) {
//		if(flag==100) {
//			
//		}
//		int sum = 0;
//		for (int arg : args)
//			sum += arg;
//		return sum;
//		return Collections.EMPTY_LIST;
//	}
	
	
}
