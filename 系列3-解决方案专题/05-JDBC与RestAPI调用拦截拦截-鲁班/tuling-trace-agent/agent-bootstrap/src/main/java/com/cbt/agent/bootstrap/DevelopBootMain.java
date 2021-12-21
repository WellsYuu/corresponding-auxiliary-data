package com.cbt.agent.bootstrap;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.UUID;

/**
 * 
 * Description:开发版引导程序<br/>
 * 
 * @author zengguangwei@bubugao.com
 * @date: 2016年12月29日 下午3:29:02
 * @version 1.0
 * @since JDK 1.7
 */
public class DevelopBootMain {
    private static final String DEFAULT_APP_NAME = "com.cbt.agent.core.DefaultApplication";

    // 在应用启动前调用
    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        String defaultProperties = null;
        if (agentArgs != null && !agentArgs.trim().equals("")) {
            defaultProperties = agentArgs.replaceAll(",", "\n");
        }
        Properties config = AgentBootMain.loadLocalProperties(defaultProperties,"/conf/cbt_dev.properties");
        File fastFile = new File(AgentBootMain.getAgentRootPath(), "fastjson-1.1.23.jar");
        if (!fastFile.exists()) {
            throw new RuntimeException("藏宝图服务启动失败:找到不文件" + fastFile.toString());
        }
        config.setProperty(AgentBootMain.FAST_JSON_URL, "file:" + fastFile.toString());

        String devBase = config.getProperty("dev.base");
        addPath((URLClassLoader) DevelopBootMain.class.getClassLoader(), devBase);
        Class<?> appClass = Class.forName(DEFAULT_APP_NAME);
        AgentApplication application = (AgentApplication)  appClass.getMethod("getInstance").invoke(appClass);
        application.init(mockSession(config), config, config.getProperty("dev.collects").split("&"),inst);

        inst.addTransformer(application.getTransformer());
        System.err.println("cbt 开发服务启动成功 ");
    }

    private static CbtSessionInfo mockSession(Properties config) {
        CbtSessionInfo session = new CbtSessionInfo();
        session.setAppId("dev.app");
        session.setLoginTime(System.currentTimeMillis());
        session.setClientMd5(UUID.randomUUID().toString().replaceAll("-", ""));
        session.setClientVersion(config.getProperty("mock.version","cbt-agent_V1.1.2"));
        session.setConfigs(new Properties());
        // 添加配置。开发模式下远程共用本地配置
        session.getConfigs().putAll(config);

        session.setProKey(config.getProperty(AgentBootMain.PRO_KEY));
        session.setSessionId(config.getProperty("mock.sessionId","384"));
        return session;

    }

    private static void addPath(URLClassLoader loader, String path) throws Exception {
        Method addurl_method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addurl_method.setAccessible(true);
        URL clientFile = new File(path).toURI().toURL();
        addurl_method.invoke(loader, clientFile.toURI().toURL());
    }

}
