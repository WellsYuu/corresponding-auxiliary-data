package com.cbt.agent.collect;

public enum EventType {

    empty, redis,dubbo_consumer,
    dubbo_provider,
    spring_mvc,
    JDBC,
    http_api,
    servlet, JMS_SEND_TEMPLATE, jms_listener_spring, jedis,threadPool;
}
