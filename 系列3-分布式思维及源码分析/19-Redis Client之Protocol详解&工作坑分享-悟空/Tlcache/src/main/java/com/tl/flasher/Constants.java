package com.tl.flasher;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Redis服务的一些常量配置
 * <p/>
 */
public final class Constants {
    private Constants(){}

    /**
     * flasher 默认域名与key的连接分隔字符
     */
    public static final String DEFAULT_SEPARATOR = ":";
    /**
     * redis集群的timeout时长(毫秒)
     */
    public static final int DEFAULT_CLUSTER_TIMEOUT = 2000;
    /**
     *
     */
    public static final int DEFAULT_CLUSTER_MAX_REDIRECTIONS = 500;

    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 监控指标本地存储
     */
    public static final Map<String,Integer> MONITOR_MAP = Maps.newConcurrentMap();
    // 使用次数
    public static final String MONITOR_GEDIS_USED_NUM_NAME = "USED_NUM";
    // 使用时长(ms)
    public static final String MONITOR_GEDIS_USED_TIME_NAME = "USED_TIME";
    // 监控数据抽取时间(s)
    public static final int MONITOR_INTERVAL_SECONDS = 60;
    public static final String MONITOR_PUSH_TYPE_NAME = "PUSH_TYPE";
    public static final String MONITOR_PROTOCOL_NAME = "PROTOCOL";
    public static final String MONITOR_HOST_NAME = "HOST";
    public static final String MONITOR_PORT_NAME = "PORT";
    public static final String MONITOR_SERVLET_AUTO_CLEAR_TIME_NAME  = "AUTO_CLEAR_TIME";
    public static final String MONITOR_SERVLET_HAND_CLEAR_NAME  = "HAND_CLEAR";
    public static final String MONITOR_SERVLET_SCHEDULED_SERVLET_NAME  = "SCHEDULED_SERVLET";

}
