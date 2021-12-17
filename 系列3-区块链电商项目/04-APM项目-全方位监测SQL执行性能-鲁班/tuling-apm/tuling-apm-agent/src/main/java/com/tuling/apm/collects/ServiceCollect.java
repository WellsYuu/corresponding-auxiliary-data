package com.tuling.apm.collects;

import com.tuling.apm.ApmContext;
import com.tuling.apm.ICollect;
import com.tuling.apm.model.ServiceStatistics;
import javassist.*;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

/**
 * 服务接口响应性能采集
 * Created by Tommy on 2018/3/8.
 */
public class ServiceCollect extends AbstractByteTransformCollect implements ICollect {
    public static ServiceCollect INSTANCE;
    private final ApmContext context;
    private String include;
    private String exclude;

    private static final String beginSrc;
    private static final String endSrc;
    private static final String errorSrc;

    static {
        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append("com.tuling.apm.collects.ServiceCollect instance= ");
        sbuilder.append("com.tuling.apm.collects.ServiceCollect.INSTANCE;\r\n");
        sbuilder.append("com.tuling.apm.model.ServiceStatistics statistic =instance.begin(\"%s\",\"%s\");");
        beginSrc = sbuilder.toString();
        sbuilder = new StringBuilder();
        sbuilder.append("instance.end(statistic);");
        endSrc = sbuilder.toString();
        sbuilder = new StringBuilder();
        sbuilder.append("instance.error(statistic,e);");
        errorSrc = sbuilder.toString();
    }

    // include
    // exclude
    public ServiceCollect(ApmContext context, Instrumentation instrumentation) {
        super(instrumentation);
        this.context = context;
        include = context.getConfig("service.include");
        exclude = context.getConfig("service.exclude");
        INSTANCE = this;
    }

    public ServiceStatistics begin(String className, String methodName) {
        ServiceStatistics bean = new ServiceStatistics();
        bean.setRecordTime(System.currentTimeMillis());
//        bean.setHostIp();
//        bean.setHostName();
        bean.setBegin(System.currentTimeMillis());
        bean.setServiceName(className);
        bean.setMethodName(methodName);
        bean.setSimpleName(className.substring(className.lastIndexOf(".")));
        bean.setRecordModel("service");
        return bean;
    }

    public void error(ServiceStatistics bean, Throwable e) {
        bean.setErrorType(e.getClass().getSimpleName());
        bean.setErrorMsg(e.getMessage());
    }

    public void end(ServiceStatistics bean) {
        bean.setEnd(System.currentTimeMillis());
        bean.setUseTime(bean.end - bean.begin);
        context.submitCollectResult(bean);
    }


    @Override
    public byte[] transform(ClassLoader loader, String className) throws CannotCompileException, NotFoundException, IOException {
        if (!className.endsWith("ServiceImpl")) {
            return null;
        }
        CtClass ctclass = toCtClass(loader, className);
        AgentByteBuild byteLoade = new AgentByteBuild(className, loader, ctclass);
        CtMethod[] methods = ctclass.getDeclaredMethods();
        for (CtMethod m : methods) {
            // 屏蔽非公共方法
            if (!Modifier.isPublic(m.getModifiers())) {
                continue;
            }
            // 屏蔽静态方法
            if (Modifier.isStatic(m.getModifiers())) {
                continue;
            }
            // 屏蔽本地方法
            if (Modifier.isNative(m.getModifiers())) {
                continue;
            }
            AgentByteBuild.MethodSrcBuild build = new AgentByteBuild.MethodSrcBuild();
            build.setBeginSrc(String.format(beginSrc, className, m.getName()));
            build.setEndSrc(endSrc);
            build.setErrorSrc(errorSrc);
            byteLoade.updateMethod(m, build);
        }
        return byteLoade.toBytecote();
    }
}
