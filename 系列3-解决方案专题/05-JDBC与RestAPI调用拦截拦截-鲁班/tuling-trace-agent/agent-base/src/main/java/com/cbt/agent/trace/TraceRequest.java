package com.cbt.agent.trace;

import java.io.Serializable;
import java.util.Properties;

/**
 * 保存session node节点相关信息
 * 
 * @since 0.1.0
 */
public class TraceRequest implements Serializable {
    private static final long serialVersionUID = -653665743530768218L;
    // TODO 以下三个属性设定为常量更严谨
    /**
     * 调用链全局唯一标识
     */
    private String traceId;
    
    /**
     * 调用链父节点调用标识
     */
    private String parentRpcId;

    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getParentRpcId() {
        return parentRpcId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setParentRpcId(String parentRpcId) {
        this.parentRpcId = parentRpcId;
    }

}
