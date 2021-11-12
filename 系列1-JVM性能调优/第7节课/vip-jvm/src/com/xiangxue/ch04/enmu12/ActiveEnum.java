package com.xiangxue.ch04.enmu12;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：枚举和行为绑定
 */
public class ActiveEnum {
	
	public enum NormalActive{
		PLUS,MINUS,MULTI,DIVIDS,DIFFER;
		
		double oper(double x,double y) {
			switch(this) {
			case PLUS:return x+y;
			case MINUS:return x-y;
			case MULTI:return x*y;
			case DIVIDS:return x/y;
			}
			throw new UnsupportedOperationException();
		}		
	}
	
	public enum BetterActive{
		PLUS {
			@Override
			double oper(double x, double y) {
				return x+y;
			}
		},MINUS {
			@Override
			double oper(double x, double y) {
				return x-y;
			}
		};
		
		abstract double oper(double x,double y);
		
//		double oper(double x,double y) {
//			switch(this) {
//			case PLUS:return x+y;
//			case MINUS:return x-y;
//			case MULTI:return x*y;
//			case DIVIDS:return x/y;
//			
//			//default:
//			}
//			throw new UnsupportedOperationException();
//		}		
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(NormalActive.PLUS.oper(0.1, 0.2));
	}

}
