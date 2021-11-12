package com.xiangxue.ch02.reftype;

import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class TestSoftRef {
	
	public static class User{
		public int id = 0;
		public String name = "";
		public User(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}
		
	}
	
	public static void main(String[] args) {

		User u = new User(1,"Mark");
		SoftReference<User> userSoft = new SoftReference<>(u);
		u = null;//保证new User(1,"Mark")这个实例只有userSoft在软引用
		
		System.out.println(userSoft.get());
		System.gc();//展示gc的时候，SoftReference不一定会被回收
		System.out.println("AfterGc");
		System.out.println(userSoft.get());//new User(1,"Mark")没有被回收
		List<byte[]> list = new LinkedList<>();
		
		try {
			for(int i=0;i<100;i++) {
				//User(1,"Mark")实例一直存在
				System.out.println("********************"+userSoft.get());
				list.add(new byte[1024*1024*1]);
			}
		} catch (Throwable e) {
			//抛出了OOM异常后打印的，User(1,"Mark")这个实例被回收了
			System.out.println("Throwable********************"+userSoft.get());
		}
		
	}
}
