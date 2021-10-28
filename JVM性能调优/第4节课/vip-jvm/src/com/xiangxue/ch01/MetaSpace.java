package com.xiangxue.ch01;

import java.util.LinkedList;
import java.util.List;

public class MetaSpace {
	
	public static void main(String[] args) {
		
		List<Object> list = new LinkedList<>();
		int i=0;
		while(true) {
			i++;
			if(i%10000==0) System.out.println("i="+i);
			list.add(new Object());
		}
		
		//String[] strings = new String[100000000];
	}

}
