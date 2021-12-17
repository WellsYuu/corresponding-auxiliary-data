package com.tuling.apm.collects;

import javassist.*;

import java.io.IOException;

/**
 * agent 字节器 主要作用 1:构建代理监听环境 2:为目标类载入代理监听
 *
 * @author tommy
 */
public class AgentByteBuild {
    private final String className;
    private final ClassLoader loader;
    private final CtClass ctclass;

    public AgentByteBuild(String className,
                          ClassLoader loader,
                          CtClass ctclass) {
        this.className = className;
        this.loader = loader;
        this.ctclass = ctclass;
    }

    /*
    * 插入 监听 method
    */
    public void updateMethod(CtMethod method, MethodSrcBuild srcBuild) throws CannotCompileException, NotFoundException {
        CtMethod ctmethod = method;
        String methodName = method.getName();
        // 重构被代理的方法名称

        // 基于原方法复制生成代理方法
        CtMethod agentMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);
        agentMethod.setName(methodName + "$agent");
        ctclass.addMethod(agentMethod);

        // 原方法重置为代理执行
        ctmethod.setBody(srcBuild.buildSrc(ctmethod));
    }




    /**
     * 生成新的class 字节码 ，
     *
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    public byte[] toBytecote() throws IOException, CannotCompileException {
        return ctclass.toBytecode();
    }


    public static class MethodSrcBuild {
        private String beginSrc;
        private String endSrc;
        private String errorSrc;


        public MethodSrcBuild setBeginSrc(String beginSrc) {
            this.beginSrc = beginSrc;
            return this;
        }

        public MethodSrcBuild setEndSrc(String endSrc) {
            this.endSrc = endSrc;
            return this;
        }

        public MethodSrcBuild setErrorSrc(String errorSrc) {
            this.errorSrc = errorSrc;
            return this;
        }

        public String buildSrc(CtMethod method) {
            String result;
            try {
                String template = method.getReturnType().getName().equals("void") ? voidSource : source;
                String bsrc = beginSrc == null ? "" : beginSrc;
                String eSrc = errorSrc == null ? "" : errorSrc;
                String enSrc = endSrc == null ? "" : endSrc;
                String src = String.format(template, bsrc, method.getName(), eSrc, enSrc);
                return src;
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }



        final static String source = "{\n"
                + "%s"
                + "        Object result=null;\n"
                + "       try {\n"
                + "            result=($w)%s$agent($$);\n"
                + "        } catch (Throwable e) {\n"
                + "%s"
                + "            throw e;\n"
                + "        }finally{\n"
                + "%s"
                + "        }\n"
                + "        return ($r) result;\n"
                + "}\n";

        final static String voidSource = "{\n"
                + "%s"
                + "        try {\n"
                + "            %s$agent($$);\n"
                + "        } catch (Throwable e) {\n"
                + "%s"
                + "            throw e;\n"
                + "        }finally{\n"
                + "%s"
                + "        }\n"
                + "}\n";
    }


}