package com.cbt.agent.collect;

import java.util.Map;

/**
 * 
 * Description: 堆栈信息采集器<br/>
 * 
 * @author zengguangwei@cbtu.pro
 * @date: 2016年12月14日 上午11:03:01
 * @version 1.0
 * @since JDK 1.7
 */
public class StackCollect {

    public static void before(String className, String methodName) {
        // new Exception().getStackTrace()
    }

    public static void after(String className, String methodName) {

    }

    native StackTraceElement getStackTraceElement(int index);

    public static void main(String[] args) {
        new Throwable().getStackTrace();
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        Map<Thread, StackTraceElement[]> allstack = Thread.currentThread().getAllStackTraces();
        // System.out.println(new StackCollect().getStackTraceElement(1));
        for (StackTraceElement e : stack) {
            System.out.println(e.toString());
        }
        // for (StackTraceElement[] traceElement : allstack.values()) {
        // for (StackTraceElement e : traceElement) {
        // System.out.println(e.toString());
        // }
        // }
    }
}
