package com.tl.flasher;


import com.tl.flasher.enums.CommandTypeEnum;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyh on 2015/4/21.
 */
public class Command implements Serializable {
    private String business;
    private CommandTypeEnum commandName;
    private List<Object> parameters;

    public Command(){}

    public Command(CommandTypeEnum commandName, Object... coms) {
        this.commandName = commandName;
        parameters = Lists.newArrayList();
        for (Object s : coms) {
            parameters.add(s);
        }
    }

    public Command(CommandTypeEnum commandName, List<Object> parameters) {
        this.commandName = commandName;
        this.parameters = parameters;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public CommandTypeEnum getCommandName() {
        return commandName;
    }

    public void setCommandName(CommandTypeEnum commandName) {
        this.commandName = commandName;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return business + "," + commandName.name() + "," + parameters.toString();
    }
}
