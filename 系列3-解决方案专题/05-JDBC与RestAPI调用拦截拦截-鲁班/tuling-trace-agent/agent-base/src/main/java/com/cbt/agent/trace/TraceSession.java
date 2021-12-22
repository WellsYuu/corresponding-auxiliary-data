package com.cbt.agent.trace;

import com.cbt.agent.collect.Event;
import com.cbt.agent.collect.EventType;
import com.cbt.agent.common.util.Assert;
import com.cbt.agent.core.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前会话信息管理
 * 
 * @since 0.1.0
 */
public class TraceSession {
        /**
         * session 节点信息
         */
        private TraceRequest traceRequest;

        /**
         * 生成下个节点的调用ID
         */
        private String rpcId;

        /**
         * 用户于记录调用当中临时变量
         */
        private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

        private TraceContext context;
        private int serialNumber = 0;
        private Map<EventType, LinkedList<Event>> eventTable = new HashMap<>();

        public TraceSession(TraceContext context, TraceRequest traceRequest) {
                Assert.notNull(context);
                Assert.notNull(traceRequest);
                Assert.notNull(traceRequest.getParentRpcId());

                this.context = context;
                this.traceRequest = traceRequest;

                // TODO 临时逻辑需求，默认全部都是调式模式 。后期需要进行合理的设计
                if(traceRequest.getProperties()==null){
                    traceRequest.setProperties(new Properties());
                }
                if (!traceRequest.getProperties().containsKey(TraceContext.DEBUG_ID)) {
                    traceRequest.getProperties().setProperty(TraceContext.DEBUG_ID, traceRequest.getTraceId());
                }

        }

        /**
         * 获取当前rpcID
         * 
         * @return
         */
        public String getCurrentRpcId() {
                return rpcId == null ? "0" : rpcId;
        }

        /**
         * a-->b（0.1） b-->c (0.1.1) 生成下个节点RPCID
         * 
         * @return
         */
        public String getNextRpcId() {
                serialNumber++;
                rpcId = traceRequest.getParentRpcId() + "." + serialNumber;
                return rpcId;
        }

        /**
         * 获取traceId
         * 
         * @return
         */
        public String getTraceId() {
                return traceRequest.getTraceId();
        }

        public TraceRequest getTraceRequest() {
                return traceRequest;
        }

        public Object setAttribute(String key, Object param) {
                return attributes.put(key, param);
        }

        public Object removeAttribute(String key) {
                return attributes.remove(key);
        }

        public Object getAttribute(String key) {
                return attributes.get(key);
        }

        public TraceContext getContext() {
                return context;
        }

        public static String createTraceId() {
                return UUID.randomUUID().toString().replaceAll("-", "");
        }

        public void addNode(TraceNode node) {
            AppInfo appinfo = DefaultApplication.getInstance().getAppInfo();
            node.setAppId(appinfo.getAppId());
            node.setAppDetail(appinfo.getAppName());
            if (traceRequest.getProperties().containsKey(TraceContext.UPLOAD_PATH)) {
                TraceBeanWapper wapper = new TraceBeanWapper(node);
                wapper.setTheUploadUrl(traceRequest.getProperties().getProperty(TraceContext.UPLOAD_PATH));
                context.storeNode(wapper);
            } else {
                context.storeNode(node);
            }
        }

        public void addTraceFrom(TraceFrom from) {
            if (traceRequest.getProperties().containsKey(TraceContext.UPLOAD_PATH)) {
                TraceBeanWapper wapper = new TraceBeanWapper(from);
                wapper.setTheUploadUrl(traceRequest.getProperties().getProperty(TraceContext.UPLOAD_PATH));
                context.storeNode(wapper);
            } else {
                context.storeNode(from);
            }
        }

        public Boolean isDubug() {
                Properties properties = traceRequest.getProperties();
                if (properties.get(TraceContext.DEBUG_ID) != null) {
                        return true;
                }
                return false;
        }

    public void addEvent(Event event) {
        LinkedList<Event> eventList = eventTable.get(event.getType());
        if (eventList == null) {
            eventList = new LinkedList<Event>();
            eventTable.put(event.getType(), eventList);
        }
        eventList.add(event);
    }

    /**
     * 
     * 清除事件。<br/>
     * 该方法一般由事件框架去调用，手动调用须保证同类别事件不会同时激活。
     * 
     * @author zengguangwei@cbt.com
     * @date: 2016年7月11日 下午5:49:10
     * @version 1.0
     * 
     * @param event
     */
    public void clearEvent(Event event) {
        LinkedList<Event> eventList = eventTable.get(event.getType());
        if (eventList != null)
            eventList.remove(event);
    }

    public Event getCurrentEvent(EventType type) {
        LinkedList<Event> eventList = eventTable.get(type);
        if (eventList != null && !eventList.isEmpty()) {
            return eventList.get(eventList.size() - 1);
        }
        return null;
    }

        
}
