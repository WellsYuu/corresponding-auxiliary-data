package com.cbt.agent.core;

/**
 * Description: 监听目标应用信息<br/>
 *
 * @author tommy@cbt.com
 * @version 1.0
 * @date: 2017/3/23 10:21
 * @since JDK 1.7
 */
public class AppInfo {
    private String appId; // ID
    private String appName; // 应用名称
    private String namespace;// 命名空间即应用唯一标识
    private String rootName; // 工程名称

    public AppInfo() {
    }

    public AppInfo(String appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
}
