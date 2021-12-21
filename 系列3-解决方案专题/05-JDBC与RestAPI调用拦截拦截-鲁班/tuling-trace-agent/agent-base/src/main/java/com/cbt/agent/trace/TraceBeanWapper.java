package com.cbt.agent.trace;

import java.io.Serializable;

/**
 * http节点数据传输
 * 
 * @since 0.1.0
 */
public class TraceBeanWapper implements Serializable {
    private static final long serialVersionUID = -14461193465735408L;
    private TraceNode node;
    private TraceFrom from;
    private String theUploadUrl;// 指定上传路径

    public TraceBeanWapper(Object node) {
        if (node instanceof TraceNode)
            this.node = (TraceNode) node;
        else if (node instanceof TraceFrom)
            this.from = (TraceFrom) node;
        else
            throw new IllegalArgumentException();
    }

    public String getTheUploadUrl() {
        return theUploadUrl;
    }

    public void setTheUploadUrl(String theUploadUrl) {
        this.theUploadUrl = theUploadUrl;
    }

    public TraceNode getNode() {
        return node;
    }

    public TraceFrom getFrom() {
        return from;
    }

    public Object getBean() {
        return node != null ? node : from;
    }

}
