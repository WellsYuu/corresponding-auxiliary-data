package com.cbt.agent.collect;

import java.util.HashMap;
import java.util.Map;
import com.cbt.agent.collect.EventType;

/**
 * 
 * 事件<br/>
 * 销毁方式: 1、谁创建就有谁销毁。2、指定方法销毁。3、主动调用会话进行销毁 <br/>
 * 传递方式：1、会话参数传播。2、通过对象代理直接传播。
 * 
 * @author zengguangwei@cbt.com
 * @date: 2016年7月11日 上午11:46:49
 * @version 1.0
 * @since JDK 1.7
 */
public class Event {
    // 父级事件 --待定
    // Event parent;
    EventType type;
    boolean active;
    Object data;
    boolean init; // 是否已初始化并加载
    // 在该方法执行后，该事件将被销毁。
    // 必要性待定
    MethodInfo destoryMethod;
    Map<String,Object> attribute=new HashMap<>();

    public Event(EventType type, boolean active) {
        super();
        this.type = type;
        this.active = active;
        init = false;
    }

    public EventType getType() {
        return type;
    }

    /**
     * 
     * 关闭该事件<br/>
     * 
     * @author zengguangwei@cbt.com
     * @date: 2016年6月15日 下午3:53:23
     * @version 1.0
     * 
     */
    public void close() {
        active = false;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MethodInfo getDestoryMethod() {
        return destoryMethod;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * 
     * 绑定该事件的销毁方法 <br/>
     * 默认规则是:方法的执行前创建，执行后销毁。 所以该方法使用场景为，事件的生命周期跨越多个方法节点。 <br/>
     * 如JDBC 的 Connection.prepareStatement() 表示事件开始，Statement.close() 表示事件结束。 <br/>
     * 必须保证该方法在事件结束后执行,否则无法即时清理会话当中的event数据. <br/>
     * <span style="color:red;">注：如果事件没有执行到该方法必须调用
     * {@linkplain TraceSession#clearEvent} 手动清除</span>
     * 
     * @author zengguangwei@cbt.com
     * @date: 2016年6月17日 下午4:05:16
     * @version 1.0
     * 
     * @param className 对应类名（必填）。
     * @param methodName 对应方法名（必填）。
     * @param paramTypes 方法参数类别。如果为则空表示符合条件的任一方法（可选）。
     */
    public void setDestoryMethod(String className, String methodName, String[] paramTypes) {
        this.destoryMethod = new MethodInfo(className, methodName, paramTypes);
    }

    @Override
    public String toString() {
        return "Event [type=" + type + ", active=" + active + ", data=" + data + ", run=" + init + "]";
    }

    public Object getAttribute(String key ){
        return attribute.get(key);
    }

    public void setAttribute(String key , Object value){
         attribute.put(key,value);
    }



}
