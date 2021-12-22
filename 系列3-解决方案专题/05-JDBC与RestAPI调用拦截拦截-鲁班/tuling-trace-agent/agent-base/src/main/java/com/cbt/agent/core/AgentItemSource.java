package com.cbt.agent.core;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by tommy on 16/11/2.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class AgentItemSource implements java.io.Serializable {
    @XmlAttribute(name="target")
    private String targetClassName;

    @XmlAttribute(name="handle")
    private String handleClassName;

    @XmlElement(name="method")
    MethodInfo methodInfo;

    @XmlElement
    private String beforeSrc;

    @XmlElement
    private String afterSrc;

    @XmlAttribute(name="template")
    private String srcTemplate;

    @XmlAttribute
    private AgentFinal.AgentWay way;




    public String getTargetClassName() {
        return targetClassName;
    }

    public void setTargetClassName(String targetClassName) {
        this.targetClassName = targetClassName;
    }

    public void setMethodInfo(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
    }

    public String getHandleClassName() {
        return handleClassName;
    }

    public void setHandleClassName(String handleClassName) {
        this.handleClassName = handleClassName;
    }

    public String getBeforeSrc() {
        return beforeSrc;
    }

    public void setBeforeSrc(String beforeSrc) {
        this.beforeSrc = beforeSrc;
    }

    public String getAfterSrc() {
        return afterSrc;
    }

    public void setAfterSrc(String afterSrc) {
        this.afterSrc = afterSrc;
    }

    public AgentFinal.AgentWay getWay() {
        return way;
    }

    public void setWay(AgentFinal.AgentWay way) {
        this.way = way;
    }

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }


    public String getSrcTemplate() {
        return srcTemplate;
    }

    public void setSrcTemplate(String srcTemplate) {
        this.srcTemplate = srcTemplate;
    }


}
