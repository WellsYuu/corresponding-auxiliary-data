package com.cbt.agent.trace;

import com.cbt.agent.common.JsonSerializable;
import com.cbt.agent.common.JsonToStringBuilder;

/**
 * 
 * Description: 跟踪来源，调用发起点<br/>
 * 
 * @author zengguangwei
 * @date: 2016年4月1日 下午3:56:00
 * @version 1.0
 * @since JDK 1.7
 */
public class TraceFrom implements java.io.Serializable, JsonSerializable {
    
    private static final long serialVersionUID = 6285295164453622441L;
    
    private String traceId; // 跟踪id
    private String source; // 来源PC、H5、Global_APP
    private String accessPath; // 访问路径，标识访问地址，该值可用于关联系统用例
    private String refererPath; // 引用页面
    private String clientIp; // 客户端ip
    private String clientId; // 客户端标识
    private String debugId;// 调式id
    private String traceLevel; // debug、info、update、error
    private String debugParams; // 调试参数
    private Long createTime; // 记录创建时间

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getRefererPath() {
        return refererPath;
    }

    public void setRefererPath(String refererPath) {
        this.refererPath = refererPath;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDebugId() {
        return debugId;
    }

    public void setDebugId(String debugId) {
        this.debugId = debugId;
    }

    public String getTraceLevel() {
        return traceLevel;
    }

    public void setTraceLevel(String traceLevel) {
        this.traceLevel = traceLevel;
    }

    public String getDebugParams() {
        return debugParams;
    }

    public void setDebugParams(String debugParams) {
        this.debugParams = debugParams;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        if (traceId != null)
            builder.appendItem("traceId", traceId);
        if (source != null)
            builder.appendItem("source", source);
        if (accessPath != null)
            builder.appendItem("accessPath", accessPath);
        if (refererPath != null)
            builder.appendItem("refererPath", refererPath);
        if (clientIp != null)
            builder.appendItem("clientIp", clientIp);
        if (clientId != null)
            builder.appendItem("clientId", clientId);
        if (debugId != null)
            builder.appendItem("debugId", debugId);
        if (traceLevel != null)
            builder.appendItem("traceLevel", traceLevel);
        if (debugParams != null)
            builder.appendItem("debugParams", debugParams);
        if (createTime != null)
            builder.appendItem("createTime", createTime);
        return builder.toString();
    }

    @Override
    public String toJsonString() {
        return null;
    }
    
}
