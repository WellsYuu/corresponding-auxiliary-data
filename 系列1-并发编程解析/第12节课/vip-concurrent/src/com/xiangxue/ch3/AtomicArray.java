package com.xiangxue.ch3;

import java.util.concurrent.atomic.AtomicIntegerArray;


/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class AtomicArray {
    static int[] value = new int[]{1, 2};

    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(0, 3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);

    }
}
