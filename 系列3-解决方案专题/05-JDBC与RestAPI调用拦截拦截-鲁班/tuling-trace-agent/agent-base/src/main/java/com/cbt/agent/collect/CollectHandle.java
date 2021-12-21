package com.cbt.agent.collect;


public interface CollectHandle {
    public Event invokerBefore(Event event, InParams in);

    public void invokerAfter(Event event, OutResult out);

    /**
     * 配置对应采集的类别
     * @return
     */
    public EventType getEventType();
}
