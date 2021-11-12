package com.xiangxue.ch7.performance;

import java.util.HashSet;
import java.util.Set;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：减少锁的粒度
 */
public class FinenessLock {
	
	public final Set<String> users = new HashSet<String>();
	public final Set<String> queries = new HashSet<String>();
	
	public void addUser(String u) {
		synchronized (users) {
			users.add(u);
		}
	}
	
	public void addQuery(String q) {
		synchronized (users) {
			queries.add(q);
		}
	}
	
//	public synchronized void removeUser(String u) {
//		users.remove(u);
//	}
//	
//	public synchronized void removeQuery(String q) {
//		queries.remove(q);
//	}	

}
