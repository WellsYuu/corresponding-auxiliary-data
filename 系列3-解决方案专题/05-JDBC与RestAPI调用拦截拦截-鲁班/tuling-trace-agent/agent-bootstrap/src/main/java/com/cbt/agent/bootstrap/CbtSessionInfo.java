package com.cbt.agent.bootstrap;

import java.util.Date;
import java.util.Properties;

/**
 * Created by tommy on 16/11/6.
 */

public class CbtSessionInfo implements java.io.Serializable {
    private String sessionId; // 会话ID,唯一性

    private String proKey; // 项目KEY

    private String appId; // 应用ID

    private String clientVersion; // 客户端版本

    private String clientMd5; // 客户端MD5验证码

    private String[] clientUploadUrls; // 客户端更新地址

    private Long loginTime; // 登陆时间

    private Properties configs; // 属性配置

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getProKey() {
        return proKey;
    }

    public void setProKey(String proKey) {
        this.proKey = proKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientMd5() {
        return clientMd5;
    }

    public void setClientMd5(String clientMd5) {
        this.clientMd5 = clientMd5;
    }

    public String[] getClientUploadUrls() {
        return clientUploadUrls;
    }

    public void setClientUploadUrls(String[] clientUploadUrls) {
        this.clientUploadUrls = clientUploadUrls;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Properties getConfigs() {
        return configs;
    }

    public void setConfigs(Properties configs) {
        this.configs = configs;
    }
}
