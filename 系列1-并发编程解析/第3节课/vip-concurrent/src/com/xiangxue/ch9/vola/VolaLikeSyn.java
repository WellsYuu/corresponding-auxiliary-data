package com.xiangxue.ch9.vola;

public class VolaLikeSyn {
    long i = 0L;  // 普通的long型变量

    public synchronized long getI() {    // 对单个的普通变量的写用同一个锁同步
        return i;  
    }

    public synchronized void setI(long i) {  // 对单个的普通变量的读用同一个锁同步
        this.i = i;  // 单个volatile变量的写
    }
    
    public void inc() {        // 普通方法调用
    	long temp = getI();    // 调用已同步的读方法
    	temp = temp + 1L;      // 普通写操作
    	setI(temp);            // 调用已同步的写方法
    }
}
