package com.xiangxue.ch5;

import java.util.concurrent.ConcurrentHashMap;

public class TestMap18 {
	public static void main(String[] args) {
//		ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
//		map.put("mark", "mark");
//		map.get("bill");
		
		
		int c = 13;
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        n=n+1;
        System.out.println("n="+n);
        
        //String key = "mark";
//        int h = 22334534;
//        int spreadHashCode = (h ^ (h >>> 16)) & 0x7fffffff;
//        System.out.println("h="+h+"="+Integer.toBinaryString(h));
//        System.out.println("s="+spreadHashCode+"="+Integer.toBinaryString(spreadHashCode));
	}

}
