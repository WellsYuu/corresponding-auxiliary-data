package com.cbt.agent.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by tommy on 16/11/5.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SrcTemplate {
    @XmlAttribute
    private String name;
    @XmlValue
    private String value;
    public SrcTemplate(){

    }

    public SrcTemplate(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
