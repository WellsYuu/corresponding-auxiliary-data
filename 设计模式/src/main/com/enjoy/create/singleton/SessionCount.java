package com.enjoy.create.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class SessionCount {
    private AtomicInteger count = new AtomicInteger(0);

    private static SessionCount instance;//懒汉式

    private SessionCount(){
    }

    //获取唯一可用的对象
    //懒汉式
    public static SessionCount getInstance(){
        if (instance == null){//双重校验锁
            synchronized (SessionCount.class){
                if (instance == null){
                    instance = new SessionCount();
                }
            }
        }

        return instance;
    }

    /***以下是业务方法***/
    public int plus(){
        return count.incrementAndGet();
    }

    public int decrease(){
        return count.decrementAndGet();
    }

    public void showMessage(){
        System.out.println("当前人数："+this.count.get());
    }


}
