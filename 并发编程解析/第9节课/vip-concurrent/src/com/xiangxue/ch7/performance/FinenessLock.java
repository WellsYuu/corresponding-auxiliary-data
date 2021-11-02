package com.xiangxue.ch7.performance;

import java.util.HashSet;
import java.util.Set;

public class FinenessLock {
	
	public final Set<String> users = new HashSet<String>();
	public final Set<String> queries = new HashSet<String>();
	
	public synchronized void addUser(String u) {
		users.add(u);
	}
	
	public synchronized void addQuery(String q) {
		queries.add(q);
	}
	
	public synchronized void removeUser(String u) {
		users.remove(u);
	}
	
	public synchronized void removeQuery(String q) {
		queries.remove(q);
	}	

}
