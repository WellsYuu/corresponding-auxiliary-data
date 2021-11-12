package com.xiangxue.ch7.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：不可变的类
 */
public class ImmutetableToo {
	private List<Integer> list =  new ArrayList<>(3);
	
	public ImmutetableToo() {
		list.add(1);
		list.add(2);
		list.add(3);
	}
	
	public boolean isContains(int i) {
		return list.contains(i);
	}

}
