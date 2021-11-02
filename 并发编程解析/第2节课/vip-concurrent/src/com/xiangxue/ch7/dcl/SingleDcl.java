package com.xiangxue.ch7.dcl;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 * 懒汉式-双重检查
 */
public class SingleDcl {
    private volatile static SingleDcl singleDcl;
    private SingleDcl(){
    }

    public static SingleDcl getInstance(){
    	if(singleDcl==null) {
    		synchronized (SingleDcl.class) {//类锁
				if(singleDcl==null) {
					singleDcl = new SingleDcl();
				}
			}
    	}
        return singleDcl;
    }
}
