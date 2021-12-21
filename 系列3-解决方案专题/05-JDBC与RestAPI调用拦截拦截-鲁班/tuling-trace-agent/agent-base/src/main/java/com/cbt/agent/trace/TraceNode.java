package com.cbt.agent.trace;

import java.io.Serializable;

import com.cbt.agent.common.JsonSerializable;
import com.cbt.agent.common.JsonToStringBuilder;

/**
 * 跟踪节点
 * 
 * @since 0.1.0
 */
public class TraceNode implements Serializable, JsonSerializable {
    private static final long serialVersionUID = -8156032079009497957L;
    /**
     * 调用链全局唯一标识
     */
    private String traceId;
    /**
     * 调用链节点调用标识，表明执行顺序，与嵌套层次
     */
    private String rpcId;
    
    private String appId;
    private String appDetail;
    
    /**
     * 节点类型
     */
    private String nodeType;
    /**
     * 返回状态
     */
    private String resultState;
    /**
     * 返回结果大小
     */
    private String resultSize;
    /**
     * 调用服务路径
     */
    private String servicePath;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 开始时间 单位：毫秒
     */
    private Long beginTime;
    /**
     * 结束时间 单位：毫秒
     */
    private Long endTime;
    /**
     * 目标ip
     */
    private String addressIp;
    /**
     * 来源ip
     */
    private String fromIp;
    /**
     * 输入参数
     */
    private String inParam;
    /**
     * 输出参数
     */
    private String outParam;
    /**
     * 异常信息
     */
    private String errorMessage;
    /**
     * 异常堆栈
     */
    private String errorStack;
    /**
     * 堆堆信息
     */
    private String stackNodes;

    public String getTraceId() {
        return traceId;
    }
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
    public String getRpcId() {
        return rpcId;
    }
    public void setRpcId(String rpcId) {
        this.rpcId = rpcId;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppDetail() {
        return appDetail;
    }
    public void setAppDetail(String appDetail) {
        this.appDetail = appDetail;
    }
    public String getNodeType() {
        return nodeType;
    }
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
    public String getResultState() {
        return resultState;
    }
    public void setResultState(String resultState) {
        this.resultState = resultState;
    }
    public String getResultSize() {
        return resultSize;
    }
    public void setResultSize(String resultSize) {
        this.resultSize = resultSize;
    }

    public String getServicePath() {
        return servicePath;
    }
    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public Long getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }
    public Long getEndTime() {
        return endTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public String getAddressIp() {
        return addressIp;
    }
    public void setAddressIp(String addressIp) {
        this.addressIp = addressIp;
    }
    public String getFromIp() {
        return fromIp;
    }
    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }
    public String getInParam() {
        return inParam;
    }
    public void setInParam(String inParam) {
        this.inParam = inParam;
    }
    public String getOutParam() {
        return outParam;
    }
    public void setOutParam(String outParam) {
        this.outParam = outParam;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorStack() {
        return errorStack;
    }
    public void setErrorStack(String errorStack) {
        this.errorStack = errorStack;
    }

    public String getStackNodes() {
        return stackNodes;
    }

    public void setStackNodes(String stackNodes) {
        this.stackNodes = stackNodes;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("TraceNode{");
        sb.append("traceId='").append(traceId).append('\'');
        sb.append(", rpcId='").append(rpcId).append('\'');
        sb.append(", appId='").append(appId).append('\'');
        sb.append(", appDetail='").append(appDetail).append('\'');
        sb.append(", nodeType='").append(nodeType).append('\'');
        sb.append(", resultState='").append(resultState).append('\'');
        sb.append(", resultSize='").append(resultSize).append('\'');
        sb.append(", servicePath='").append(servicePath).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", addressIp='").append(addressIp).append('\'');
        sb.append(", fromIp='").append(fromIp).append('\'');
        sb.append(", inParam='").append(inParam).append('\'');
        sb.append(", outParam='").append(outParam).append('\'');
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", errorStack='").append(errorStack).append('\'');
        sb.append(", stackNodes='").append(stackNodes).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String toJsonString() {
        return toString();
    }

}
