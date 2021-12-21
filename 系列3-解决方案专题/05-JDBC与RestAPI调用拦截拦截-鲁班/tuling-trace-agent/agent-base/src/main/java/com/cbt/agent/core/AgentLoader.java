package com.cbt.agent.core;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

import com.cbt.agent.common.util.Assert;
import com.cbt.agent.common.util.IOUtils;
import com.cbt.agent.common.util.JarUtil;
import com.cbt.agent.common.util.JaxbUtil;

/**
 * agent class 装载器 主要作用 1:构建代理监听环境 2:为目标类载入代理监听
 */
public class AgentLoader {
    private static final String CLASS_FILE_TEMP = "./temp";
    final List<AgentPlugin> plugins = new ArrayList<>();
    Map<String, ClassLoader> initLoaders = new HashMap<>();


    public AgentLoader() {
    }

    public void loadItemByJar(URL urlJar) throws Exception {
        // 解析collectsJar文件
        // copy 至临时目录
        JarFile jarFile = JarUtil.retrieve(urlJar);
        JarEntry configEntry = jarFile.getJarEntry("agentConfig/agent.xml");
        if (configEntry != null) {
            InputStream input = jarFile.getInputStream(configEntry);
            try {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                IOUtils.copy(input, output);
                AgentPlugin agentPlugin = JaxbUtil.converyToJavaBean(output.toString("UTF-8"), AgentPlugin.class);
                //验证数据完整性
                checkPlugin(agentPlugin);

                // 加载src模板
                for (AgentItemSource item : agentPlugin.getAgentItems()) {
                    for (SrcTemplate srcTemplate : agentPlugin.getTemplates()) {
                        if (item.getSrcTemplate().equals(srcTemplate.getName())) {
                            item.setSrcTemplate(srcTemplate.getValue());
                        }
                    }
                }
                agentPlugin.setRelysClassPath(new URL[]{urlJar});
                plugins.add(agentPlugin);
            } finally {
                input.close();
            }
        } else {
            throw new Exception(urlJar.toString() + " not found agentConfig/agent.xml");
        }
    }

    private AgentPlugin parseItem(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copy(input, output);
        AgentPlugin agentPlugin = JaxbUtil.converyToJavaBean(output.toString("UTF-8"), AgentPlugin.class);
        // 验证数据完整性
        checkPlugin(agentPlugin);

        // 加载src模板
        for (AgentItemSource item : agentPlugin.getAgentItems()) {
            for (SrcTemplate srcTemplate : agentPlugin.getTemplates()) {
                if (item.getSrcTemplate().equals(srcTemplate.getName())) {
                    item.setSrcTemplate(srcTemplate.getValue());
                }
            }
        }
        return agentPlugin;

    }

    public void loadItemByDirectory(File dir) throws Exception {
        File agentConfig = new File(dir, "/agentConfig/agent.xml");
        if (!agentConfig.exists()) {
            throw new RuntimeException("not fonud" + agentConfig.toString());
        }
        FileInputStream input = new FileInputStream(agentConfig);
        AgentPlugin agentPlugin;
        try {
            agentPlugin = parseItem(input);
        } finally {
            input.close();
        }
        agentPlugin.setRelysClassPath(new URL[]{dir.toURI().toURL()});
        plugins.add(agentPlugin);
    }

    private void checkPlugin(AgentPlugin agentPlugin) {
        //TODO 验证plugin 数据完整性
    }


    // 是否为监控目标
    public boolean isTarget(String className) {
        Assert.notNull(className);
        return !getItems(className).isEmpty();
    }

    public byte[] loadClassByte(String className, ClassLoader loader) throws Exception {
        if (!isTarget(className))
            return null;
        List<AgentItemSource> agents = getItems(className);
        if (agents.isEmpty()) {
            return null;
        }
        Assert.notEmpty(agents);
        initClassLoader(className, loader);
        CtClass reuslt = buildAgentClass(className, loader, agents);
        return reuslt.toBytecode();
    }

    public Class loadClass(String className, ClassLoader loader) throws Exception {
        List<AgentItemSource> agents = getItems(className);
        if (agents.isEmpty()) {
            return null;
        }
        initClassLoader(className, loader);
        CtClass reuslt = buildAgentClass(className, loader, agents);
        return reuslt.toClass();
    }

    public void initClassLoader(String className, ClassLoader loader) throws Exception {
        if (loader instanceof URLClassLoader) {
            for (URL url : getRelysClassPath(className)) {
                String key = loader.hashCode() + "_" + url.hashCode();
                if (!initLoaders.containsKey(key)) {
                    JarUtil.addUrl(url, (URLClassLoader) loader);
                }
            }
        } else {
            throw new RuntimeException("init agent load fail,ClassLoader must URLClassLoader Realistic : " + loader.getClass().getName());
        }
//            HttpAgentProxyHandle.initializeHttp();
    }

    public Object[] getAgentPlugins() {
        return plugins.toArray(new AgentPlugin[plugins.size()]);
    }

    private List<AgentItemSource> getItems(String className) {
        List<AgentItemSource> result = new ArrayList<>();
        for (AgentPlugin plugin : plugins) {
            for (AgentItemSource agentItemSource : plugin.getAgentItems()) {
                if (agentItemSource.getTargetClassName().equals(className)) {
                    result.add(agentItemSource);
                }
            }
        }
        return result;
    }

    private List<URL> getRelysClassPath(String className) {
        List<URL> result = new ArrayList<>();
        for (AgentPlugin plugin : plugins) {
            for (AgentItemSource agentItemSource : plugin.getAgentItems()) {
                if (agentItemSource.getTargetClassName().equals(className)) {
                    result.addAll(Arrays.asList(plugin.getRelysClassPath()));
                    break;
                }
            }
        }
        return result;
    }

    public CtClass buildAgentClass(String className, ClassLoader loader, List<AgentItemSource> agents) throws NotFoundException,
            CannotCompileException, IOException {
        ClassPool classPool = new ClassPool(false);
        classPool.insertClassPath(new LoaderClassPath(loader));
        CtClass ctclass = classPool.get(className);
        /*
         * 插入 监听 method
         */
        for (AgentItemSource agentItem : agents) {
            MethodInfo methodInfo = agentItem.getMethodInfo();
            CtMethod ctmethod;
            if (methodInfo.getParamTypes() != null) {
                CtClass[] paramsTypes = classPool.get(methodInfo.getParamTypes());
                ctmethod = ctclass.getDeclaredMethod(methodInfo.getMethodName(), paramsTypes);
            } else
                ctmethod = ctclass.getDeclaredMethod(methodInfo.getMethodName());
            String newMethodName = methodInfo.getMethodName() + "$agent";
            ctmethod.setName(newMethodName);
            CtMethod newMethod = CtNewMethod.copy(ctmethod, methodInfo.getMethodName(), ctclass, null);
            newMethod.setBody(buildSrc(agentItem));
            ctclass.addMethod(newMethod);
        }
        ctclass.writeFile(CLASS_FILE_TEMP);
        String outlog = String.format("cbt anget writeFile %s%s/%s", System.getProperty("user.dir"), CLASS_FILE_TEMP, ctclass
                .getName().replaceAll("[.]", "/") + ".class");
        System.out.println(outlog);
        return ctclass;
    }

    private String buildSrc(AgentItemSource sources) {
        String targetClassName = sources.getTargetClassName();
        MethodInfo methodInfo = sources.getMethodInfo();
        String beforeSrc = sources.getBeforeSrc();
        String afterSrc = sources.getAfterSrc();
        String handClassName = sources.getHandleClassName();
        String srcTemplate = sources.getSrcTemplate();
        // TODO 生成源码
        Properties params = new Properties();
        params.put("target.className", targetClassName);
        params.put("target.methodName", methodInfo.getMethodName());
        String argsSrc = "";
        if (methodInfo.getParamTypes() != null) {
            String typeText = "";
            StringBuffer argsBuffer = new StringBuffer();
            int i = 0;
            for (String s : methodInfo.getParamTypes()) {
                typeText += "," + s;
                argsBuffer.append(",(" + s + ")_param.args[" + i + "]");
                i++;
            }
            typeText = typeText.substring(1);
            params.put("target.paramTypes", typeText);
            argsBuffer.deleteCharAt(0);
            argsSrc = argsBuffer.toString();
        } else {
            argsSrc = "$$";
        }
        params.put("target.args", argsSrc);
        if (beforeSrc != null)
            params.put("beforeSrc", beforeSrc);
        if (afterSrc != null)
            params.put("afterSrc", afterSrc);
        params.put("handle.className", handClassName);
        String src = null;
//        srcTemplate=sources.getSrcTemplate();
        src = replace(sources.getSrcTemplate(), params);
        return src;
    }

    protected static String replace(String str, Properties params) {
        // Implementation is copied from ClassLoaderLogManager.replace(),
        // but added special processing for catalina.home and catalina.base.
        String result = str;
        int pos_start = str.indexOf("${");
        if (pos_start >= 0) {
            StringBuilder builder = new StringBuilder();
            int pos_end = -1;
            while (pos_start >= 0) {
                builder.append(str, pos_end + 1, pos_start);
                pos_end = str.indexOf('}', pos_start + 2);
                if (pos_end < 0) {
                    pos_end = pos_start - 1;
                    break;
                }
                String propName = str.substring(pos_start + 2, pos_end);
                String replacement;
                if (propName.length() == 0) {
                    replacement = null;
                } else {
                    replacement = params.getProperty(propName, "");
                }
                if (replacement != null) {
                    builder.append(replacement);
                } else {
                    builder.append(str, pos_start, pos_end + 1);
                }
                pos_start = str.indexOf("${", pos_end + 1);
            }
            builder.append(str, pos_end + 1, str.length());
            result = builder.toString();
        }
        return result;
    }

    public static final String return_Src_Template =
            "{\n"
                    + "com.cbt.agent.collect.InParams _param=new com.cbt.agent.collect.InParams();\n"
                    + "        com.cbt.agent.collect.OutResult _result=new com.cbt.agent.collect.OutResult();\n"
                    + "        _param.args=$args;\n"
                    + "        _result.args=$args;\n"
                    + "        _param.className=\"${target.className}\";\n"
                    + "        _param.methodName=\"${target.methodName}\";\n"
                    + "        _result.className=\"${target.className}\";\n"
                    + "        _result.methodName=\"${target.methodName}\";\n"
                    + "        _param.paramTypes=\"${target.paramTypes}\".split(\",\");\n"
                    + "        _param.paramTypes=\"${target.paramTypes}\".split(\",\");\n"
                    + "${beforeSrc}\n"
                    + "        com.cbt.agent.collect.CollectHandle handle=com.cbt.agent.collect.CollectHandleBeanFactory.getBean(${handle.className}.class);\n"
                    + "        com.cbt.agent.collect.Event event = handle.invokerBefore(null,_param);\n"
                    + "        try {\n"
                    + "            _result.result=($w)${target.methodName}$agent($$);\n"
                    + "        } catch (Throwable e) {\n"
                    + "            _result.error=e;\n"
                    + "            throw e;\n"
                    + "        }finally{\n"
                    + "${afterSrc}\n"
                    + "            handle.invokerAfter(event, _result);\n"
                    + "        }\n"
                    + "        return ($r) _result.result;\n"
                    + "}\n";


}