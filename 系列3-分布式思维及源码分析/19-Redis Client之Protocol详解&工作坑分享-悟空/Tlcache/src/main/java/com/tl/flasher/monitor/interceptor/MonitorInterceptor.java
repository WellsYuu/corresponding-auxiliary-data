package com.tl.flasher.monitor.interceptor;

import com.tl.flasher.Constants;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */
public class MonitorInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //调用目标方法之前执行的动作
//        System.out.println("调用方法之前: invocation对象：[" + methodInvocation + "]");
        long beginTime = System.currentTimeMillis();
        //调用目标方法
        Object rval = methodInvocation.proceed();
        long endTime = System.currentTimeMillis();
        StringBuilder methodFullName = new StringBuilder("[");
        methodFullName.append(methodInvocation.getMethod());
        methodFullName.append("]");
        String usedNumName = methodFullName + Constants.DEFAULT_SEPARATOR + Constants.MONITOR_GEDIS_USED_NUM_NAME;
        String usedTimeName = methodFullName + Constants.DEFAULT_SEPARATOR + Constants.MONITOR_GEDIS_USED_TIME_NAME;
        Integer usedNum = Constants.MONITOR_MAP.get(usedNumName);
        if(usedNum == null){
            usedNum = 0;
        }
        usedNum +=1;
        Integer usedTime = Constants.MONITOR_MAP.get(usedTimeName);
        if(usedTime == null){
            usedTime = 0;
        }
        usedTime += Long.bitCount(endTime - beginTime);
        Constants.MONITOR_MAP.put(usedNumName, usedNum);
        Constants.MONITOR_MAP.put(usedTimeName, usedTime);
//        System.out.println("调用方法: invocation对象：[" + methodInvocation.getMethod() + "]"
//                + " Run time is " + usedTime +" ms");
//        System.out.println(Constants.MONITOR_MAP);
        //调用目标方法之后执行的动作
//        System.out.println("调用结束...");
        return rval;
    }
}
