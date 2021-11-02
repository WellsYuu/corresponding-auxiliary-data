package com.xiangxue.ch7.dcl;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 * 懒汉式-类初始化模式
 */
public class SingleInit {
    private SingleInit(){}

    private static class InstanceHolder{
        public static SingleInit instance = new SingleInit();
    }

    public static SingleInit getInstance(){
        return InstanceHolder.instance;
    }

}
