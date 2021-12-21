package com.cbt.agent.core;

import javax.xml.bind.annotation.*;
import java.net.URL;
import java.util.List;

/**
 * Created by tommy on 16/11/5.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AgentPlugin")
public class AgentPlugin {
    @XmlElement
    private String describe;
    @XmlElement
    private URL[] relysClassPath; // 依赖包路径

    @XmlElement(name="template")
    private SrcTemplate[] templates;


    @XmlElement(name = "agent")
    private List<AgentItemSource> agentItems;


    public List<AgentItemSource> getAgentItems() {
        return agentItems;
    }

    public void setAgentItems(List<AgentItemSource> agentItems) {
        this.agentItems = agentItems;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public URL[] getRelysClassPath() {
        return relysClassPath;
    }

    void setRelysClassPath(URL[] relysClassPath) {
        this.relysClassPath = relysClassPath;
    }

    public SrcTemplate[] getTemplates() {
        return templates;
    }

    public void setTemplates(SrcTemplate[] templates) {
        this.templates = templates;
    }
}
