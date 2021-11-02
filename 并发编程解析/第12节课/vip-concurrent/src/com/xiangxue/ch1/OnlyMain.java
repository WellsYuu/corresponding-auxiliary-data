package com.xiangxue.ch1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：java的多线程无处不在
 */
public class OnlyMain {
    public static void main(String[] args) {
    	//虚拟机线程管理的接口
    	ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    	ThreadInfo[] threadInfos = 
    	 threadMXBean.dumpAllThreads(false, false);
    	for(ThreadInfo threadInfo:threadInfos) {
    		System.out.println("["+threadInfo.getThreadId()+"]"+" "
    				+threadInfo.getThreadName());
    	}
    }
}
