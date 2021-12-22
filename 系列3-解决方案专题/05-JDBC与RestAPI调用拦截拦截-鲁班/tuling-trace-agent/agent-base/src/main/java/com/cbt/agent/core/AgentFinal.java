package com.cbt.agent.core;

import javax.xml.bind.annotation.XmlEnum;
import java.util.Properties;

public interface AgentFinal {
    public static final String OPEN = "agent.open";
    public static final String FAST_JSON_URL = "fastJson.url";
    public static final Properties LOCAL_CONFIG = new Properties();
    public static final String CLIENT_SESSION_KEY="client_session_key";
    /**
     * 远程中心基础API访问地址
     * <br/>
     * 常用的方法有:心跳发送
     */
    public static final String API_URL_KEY="sys.api.url";

    /**
     *心跳发送频率(秒)
     */
    public static final String ECHO_FREQUENCY_KEY="sys.echo.frequency";

    @XmlEnum(value = String.class)
    public enum AgentWay {
        trace;
    }

}
