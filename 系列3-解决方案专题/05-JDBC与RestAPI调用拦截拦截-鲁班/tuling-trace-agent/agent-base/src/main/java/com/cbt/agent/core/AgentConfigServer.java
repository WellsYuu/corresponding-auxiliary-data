package com.cbt.agent.core;

import com.cbt.agent.common.util.Assert;

import java.util.Properties;

/**
 * Description: 监听器配置服务统一实现<br/>
 *
 * @author tommy@cbt.com
 * @version 1.0
 * @date: 2017/3/23 10:03
 * @since JDK 1.7
 */
public class AgentConfigServer {
    private final Properties remoteConfig;
    private final Properties localConfig;
    private final String[] appIds;
    private String currentAppid;
    private String stackIncludeExpr;//
    private String stackExcludeExpr;
    private AppInfo currentAppInfo;

    /**
     *
     * @param remoteConfig 远程配置信息
     * @param localConfig 本地配置信息
     */
    public AgentConfigServer(Properties remoteConfig, Properties localConfig) {
        Assert.notNull(remoteConfig);
        Assert.notNull(localConfig);
        this.remoteConfig = remoteConfig;
        this.localConfig = localConfig;

        //
        appIds = remoteConfig.getProperty("pro.appIds", "").split(",");
        for (String key : remoteConfig.stringPropertyNames()) {

        }
    }

    /**
     * 获取指定应用的命名空间
     *
     * @param appId
     * @return
     */
    public String getNamespace(String appId) {
        return remoteConfig.getProperty("app." + appId + ".namespace");
    }

    /**
     * 获取当前项目下所有应用ID
     */
    public String[] getAppIds() {
        return appIds;
    }

    public String getCurrentAppid() {
        return currentAppid;
    }

    protected void initAppInfo(String currentAppid) {
        this.currentAppid = currentAppid;
        String prefix = "app." + currentAppid;
        stackIncludeExpr = localConfig.getProperty("stack.includes", remoteConfig.getProperty("app." + currentAppid + ".stack.includes"));
        stackExcludeExpr = localConfig.getProperty("stack.excludes", remoteConfig.getProperty("app." + currentAppid + ".stack.excludes"));
        AppInfo info = new AppInfo();
        info.setAppId(currentAppid);
        info.setAppName(remoteConfig.getProperty(prefix + ".appName", "undefined"));
        info.setNamespace(getNamespace(currentAppid));
        this.currentAppInfo = info;
    }

    public AppInfo getCurrentAppInfo() {
        return currentAppInfo;
    }

    public String getStackIncludeExpr() {
        return stackIncludeExpr;
    }

    public String getStackExcludeExpr() {
        return stackExcludeExpr;
    }

    /**
     * 获取远程中心基础API地址
     * @return
     */
    public String getRemoteApiAddress() {
        return remoteConfig.getProperty("sys.api.url", "http://api.cbt100.pro/");
    }

    /**
     * 获取心跳频率
     * 默认30(秒)
     *
     * @return
     */
    public int getEchoFrequency() {
        return Integer.parseInt(remoteConfig.getProperty("sys.echo.frequency", "30"));
    }

}
