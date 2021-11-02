package com.xiangxue.ch7.dcl;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 * 懒汉式-双重检查
 */
public class SingleDcl {
    private static SingleDcl singleDcl;
    private SingleDcl(){
    }

    public static SingleDcl getInstance(){
    	
        return singleDcl;
    }
}
