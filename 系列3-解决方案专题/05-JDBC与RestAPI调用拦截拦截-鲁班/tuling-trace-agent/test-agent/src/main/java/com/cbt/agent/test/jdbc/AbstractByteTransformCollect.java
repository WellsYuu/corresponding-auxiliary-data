package com.cbt.agent.test.jdbc;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tommy on 2018/3/8.
 */
public abstract class AbstractByteTransformCollect {
    private static Map<ClassLoader, ClassPool> classPoolMap = new ConcurrentHashMap<ClassLoader, ClassPool>();
    static Logger logger = Logger.getLogger(AbstractByteTransformCollect.class.getName());


    public AbstractByteTransformCollect(Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (loader == null)
                    return null;
                if (className == null)
                    return null;
                className = className.trim().replace("/", ".");
                try {
                    return AbstractByteTransformCollect.this.transform(loader, className);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "类插桩转换失败:", e);
                }
                return null;
            }
        });
    }

    // 插桩
    public abstract byte[] transform(ClassLoader loader, String className) throws Exception;

    protected static CtClass toCtClass(ClassLoader loader, String className) throws NotFoundException {
        if (!classPoolMap.containsKey(loader)) {
            ClassPool classPool = new ClassPool();
            classPool.insertClassPath(new LoaderClassPath(loader));
            classPoolMap.put(loader, classPool);
        }
        ClassPool cp = classPoolMap.get(loader);
        className = className.replaceAll("/", ".");
        return cp.get(className);
    }

}
