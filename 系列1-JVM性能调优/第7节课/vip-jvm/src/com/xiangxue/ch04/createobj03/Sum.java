package com.xiangxue.ch04.createobj03;

public class Sum {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		Long sum = 0L;//对象
		for(long i=0;i<Integer.MAX_VALUE;i++) {
			sum = sum+i;
			//new 20多亿的Long的实例
		}

		System.out.println("spend time:"+(System.currentTimeMillis()-start)+"ms");
	}
}
