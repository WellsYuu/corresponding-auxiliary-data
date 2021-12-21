package com.cbt.agent.test;/**
 * Created by Administrator on 2018/6/19.
 */

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/19
 **/
public class LubanAgent {
    // prepare
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("hello premain" + args);


        // classloader 监听器
        // 所有的类装载都会经过此方法

        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (!"com/cbt/agent/test/LubanServiceImpl".equals(className)) {
                    return null;
                }

                ClassPool pool = new ClassPool();
                pool.insertClassPath(new LoaderClassPath(loader));
                try {
                    CtClass ctclass = pool.get("com.cbt.agent.test.LubanServiceImpl");
                    CtMethod ctMethod = ctclass.getDeclaredMethod("hello");
                    ctMethod.insertBefore(" long begin = System.currentTimeMillis();\n" +
                            "        System.out.println(begin);");

                    /*ctMethod.insertAfter(" long end = System.currentTimeMillis();\n" +
                            "        System.out.println(end-begin);");*/

                    return ctclass.toBytecode();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new byte[]{};
            }
        });
    }
}
