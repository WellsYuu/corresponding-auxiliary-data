package com.xiangxue.ch5;

import java.util.concurrent.ConcurrentHashMap;

public class ChmDemo {

	public static void main(String[] args) {
		
		ConcurrentHashMap<String, String> map 
			= new ConcurrentHashMap<String, String>();
		map.put("001", "001_value");
		map.put("002", "002_value");
		map.put("003", "003_value");
		
		System.out.println("---------------------------------------");
		System.out.println("002="+map.get("002"));
		map.put("002", "002_value_2nd");
		System.out.println("002="+map.get("002"));
		

	}
	
//	public V putIfAbse() {
//		synchronized (this) {
//			result = get();
//			if(result == null) {
//				put()
//			}else {
//				return result;
//			}
//		}
//	}
	
}
