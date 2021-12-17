package com.tuling.javaagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by Tommy on 2018/3/6.
 */
public class MyAgent {

    /**
     * @param args
     * @param agentmain
     */
    public static void agentmain(String args, Instrumentation agentmain) {
        System.out.println(String.format("系统载入agentmain 参数%s 载入方法:premain", args));
    }

    /**
     * agent 静态入口
     *
     * @param args
     * @param inst
     */
    public static void premain(String args, Instrumentation inst) {
        System.out.println(String.format("系统载入myAgent 参数%s 载入方法:premain", args));
        System.out.println("载入C3p0Agent");
        inst.addTransformer(new C3p0Agent());
    }


}
