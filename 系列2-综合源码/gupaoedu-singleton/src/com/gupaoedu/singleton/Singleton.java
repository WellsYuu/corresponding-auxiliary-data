package com.gupaoedu.singleton;

public class Singleton implements java.io.Serializable {   
	public static Singleton INSTANCE = new Singleton();   
	protected Singleton() {  }   
	private Object readResolve() {   
		return INSTANCE;   
	}
}
