package com.xiangxue.ch9.vola;

public class Vola {
    volatile long i = 0L;  // 使用volatile声明64位的long型变量

    public long getI() {
        return i;  // 单个volatile变量的读
    }

    public void setI(long i) {
        this.i = i;  // 单个volatile变量的写
    }
    
    public void inc() {
    	i++;  // 复合（多个）volatile变量的读/写
    }
    
    
}
