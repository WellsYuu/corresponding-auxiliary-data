package com.cbt.agent.core;

import com.cbt.agent.bootstrap.AgentApplication;
import com.cbt.agent.bootstrap.CbtSessionInfo;
import com.cbt.agent.common.util.JarUtil;
import com.cbt.agent.common.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Properties;
import java.util.Set;

/**
 * Created by tommy on 16/11/2.
 */
public class DefaultApplication implements AgentApplication, ClassFileTransformer, AgentFinal {
    private AgentLoader agenLoader;

    private AgentConfigServer configServer;
    static  DefaultApplication instance;
    private String currentAppId;
    private CbtSessionInfo sessionInfo; // 当前应用会话信息

    // TODO 所有入口通过该方法获取
    public static DefaultApplication getInstance() {
        if (instance == null) {
            instance = new DefaultApplication();
        }
        return instance;
    }

    @Override
    public void init(CbtSessionInfo session, Properties localProperties, String[] collectPaths, Instrumentation inst) throws Exception {
        configServer = new AgentConfigServer(session.getConfigs(), localProperties);
        // 初始加载 agenLoader
        agenLoader = new AgentLoader();
    this.sessionInfo =session;
        // 加载指定采集器组件
        if (collectPaths != null) {
            for (String coolect : collectPaths) {
                File f = new File(coolect);
                if (f.exists() && f.isFile()) {
                    agenLoader.loadItemByJar(f.toURI().toURL());
                } else if (f.exists() && f.isDirectory()) {
                    agenLoader.loadItemByDirectory(f);
                }
            }
        }
        // 加载默认collect 包
        Set<URL> collectJarUrls = JarUtil.findSources(getClass()
                .getClassLoader(), "Cbt_collects_lib", ".jar");

        for (URL collectJarUrl : collectJarUrls) {
            agenLoader.loadItemByJar(collectJarUrl);
        }
        // 加载至静态配置
        AgentFinal.LOCAL_CONFIG.putAll(localProperties);
        AgentFinal.LOCAL_CONFIG.put(AgentFinal.CLIENT_SESSION_KEY, session);

//        startEchos(session);
    }

    /**
     * 通过后台线程 用一定的频率发送心跳
     * @param session
     */
    private void startEchos(CbtSessionInfo session) {
        final String url=configServer.getRemoteApiAddress()+"?method=client.sendEcho&sessionId="+session.getSessionId();
        Thread thread=new Thread("cbt-echo-Thread-"+configServer.getEchoFrequency()+"_Sec"){
            @Override
            public void run() {
                while (true) {
                    try {
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.setConnectTimeout(3000);
                        conn.setReadTimeout(3000);
                        InputStream i = conn.getInputStream();
                        i.read(new byte[1024]);
                        i.close();
                        conn.disconnect();
                        Thread.sleep(configServer.getEchoFrequency()*1000);
                    } catch (IOException e) {
                        new Exception("藏宝图心跳服务发送失败", e).printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public ClassFileTransformer getTransformer() {
        return this;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] result = null;
        try {
            if (currentAppId==null&&loader != null) {
                // 通过classLoader确认 当前应用信息
                affirmCurrentApp(loader);
            }
            if (agenLoader != null && className != null && loader != null) {
                String cname = className.replaceAll("[/]", ".");
                byte[] agentResult = agenLoader.loadClassByte(cname, loader);
                if (agentResult != null)
                    result = agentResult;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private void affirmCurrentApp(ClassLoader loader) {
        if (loader == null)
            return;
        for (String id : configServer.getAppIds()) {
            if (StringUtils.hasText(configServer.getNamespace(id))
                    && loader.getResource(configServer.getNamespace(id)) != null) {
                currentAppId = id;
                break;
            }
        }
        if (currentAppId != null) {
            configServer.initAppInfo(currentAppId);
        }

    }

    public AgentConfigServer getConfigServer() {
        return configServer;
    }

    public CbtSessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public AppInfo getAppInfo() {
        if( configServer.getCurrentAppInfo()==null)
            return new AppInfo("-1","undefined");
        return configServer.getCurrentAppInfo();
    }

    public void setConfigServer(AgentConfigServer configServer) {
        this.configServer = configServer;
    }
}

