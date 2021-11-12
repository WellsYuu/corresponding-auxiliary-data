package com.xiangxue.ch02.reftype;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import com.xiangxue.ch02.reftype.TestSoftRef.User;

public class TestWeakRef {
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
		WeakReference<User> userWeak = new WeakReference<>(u);
		u = null;
		System.out.println(userWeak.get());
		System.gc();
		System.out.println("AfterGc");
		System.out.println(userWeak.get());
		
	}
}
