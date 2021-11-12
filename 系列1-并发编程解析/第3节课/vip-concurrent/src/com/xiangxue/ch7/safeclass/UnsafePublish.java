package com.xiangxue.ch7.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：存在着不安全的发布
 */
public class UnsafePublish {
	//要么用线程的容器替换
	//要么发布出去的时候，提供副本，深度拷贝
	private List<Integer> list =  new ArrayList<>(3);
	
	public UnsafePublish() {
		list.add(1);
		list.add(2);
		list.add(3);
	}
	
	//讲list不安全的发布出去了
	public List<Integer> getList() {
		return list;
	}

	//也是安全的,加了锁--------------------------------
	public synchronized int getList(int index) {
		return list.get(index);
	}
	
	public synchronized void set(int index,int val) {
		list.set(index,val);
	}	
	
}
