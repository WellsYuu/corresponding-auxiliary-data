package com.cbt.agent.collect;

import com.cbt.agent.common.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class InParams {
    public String className;
    public String methodName;
    public String[] paramTypes;
    // method args
    public Object args[] = null;
    // other args
    private Map<String, Object> others = new HashMap<String, Object>();

    public InParams() {
        super();
    }


    public InParams(String className, String methodName, String[] paramTypes, Object[] args, Map<String, Object> others) {
        super();
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.args = args;
        this.others = others;
    }

    public void put(String name, Object val) {
        if (name == null)
            throw new IllegalArgumentException("param 'name' must not null");
        others.put(name, val);
    }

    public Map<String, Object> getOthers() {
        return others;
    }

    public Object getOtherArgs(String key) {
        Assert.notNull(key);
        return others.get(key);
    }

    private MethodInfo methodInfo;

    public MethodInfo getMethodInfo() {
        if (methodInfo == null) {
            methodInfo = new MethodInfo(className, methodName, paramTypes);
        }
        return methodInfo;
    }

}
