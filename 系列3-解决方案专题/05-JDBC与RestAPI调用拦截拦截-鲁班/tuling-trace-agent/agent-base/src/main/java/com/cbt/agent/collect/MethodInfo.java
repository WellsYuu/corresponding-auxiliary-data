package com.cbt.agent.collect;

import java.util.Arrays;

public class MethodInfo {
    private String className;
    private String methodName;
    private String[] paramTypes;

    public MethodInfo(String className, String methodName, String[] paramTypes) {
        super();
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MethodInfo other = (MethodInfo) obj;
        if (className == null) {
            if (other.className != null)
                return false;
        } else if (!className.equals(other.className))
            return false;
        if (methodName == null) {
            if (other.methodName != null)
                return false;
        } else if (!methodName.equals(other.methodName))
            return false;
        // 如果paramTypes为空 则不做限制.
        if (paramTypes != null) {
            if (!Arrays.equals(paramTypes, other.paramTypes))
                return false;
        }
        return true;
    }

}