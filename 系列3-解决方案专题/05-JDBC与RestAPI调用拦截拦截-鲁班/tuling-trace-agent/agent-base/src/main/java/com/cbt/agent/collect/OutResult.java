package com.cbt.agent.collect;

import java.util.HashMap;
import java.util.Map;

public class OutResult {
    public String className;
    public String methodName;
    // method args
    public Object args[] = null;
    // method result
    public Object result = null;
    // method error
    public Throwable error = null;

    // other result
    public Map<String, Object> others = new HashMap<String, Object>();

    public String[] paramTypes;
    private MethodInfo methodInfo;

    public OutResult() {

    }

    public OutResult(String className, String methodName, Object[] args, Object result, Throwable error,
            Map<String, Object> others, String[] paramTypes) {
        super();
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        this.result = result;
        this.error = error;
        this.others = others;
        this.paramTypes = paramTypes;
    }

    public MethodInfo getMethodInfo() {
        if (methodInfo == null) {
            methodInfo = new MethodInfo(className, methodName, paramTypes);
        }
        return methodInfo;
    }

    public void put(String name, Object val) {
        if (name == null)
            throw new IllegalArgumentException("param 'name' must not null");
        others.put(name, val);
    }
}