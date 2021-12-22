package com.cbt.agent.collects.jdbc;

import com.cbt.agent.collect.CollectHandle;
import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.collect.*;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * 
 * Description: jdbc 驱动处理<br/>
 * 
 * @author zengguangwei@cbt.com
 * @date: 2016年6月6日 上午10:42:09
 * @version 1.0
 * @since JDK 1.7
 */
public class JdbcDriverHandle implements CollectHandle {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDriverHandle.class);

    private static final String TARGET_CLASS = "java.sql.Driver";
    private static final String DISPATCH_NAME = "connect";
    private static Constructor<?> connProxyConstructor;

    static {
        try {
            connProxyConstructor = getProxyConstructor(Connection.class);
        } catch (Exception e) {
            logger.error("JdbcDriverHandle initialize fail", e);
        }
    }
    private static Constructor<?> getProxyConstructor(Class<?> interfaces) throws NoSuchMethodException, SecurityException {
        Class<?> clas = Proxy.getProxyClass(JdbcDriverHandle.class.getClassLoader(), interfaces);
        Constructor<?> cons = clas.getConstructor(InvocationHandler.class);
        cons.setAccessible(true);
        return cons;
    }

    public JdbcDriverHandle() {
        Assert.notNull(connProxyConstructor);
    }

    @Override
    public Event invokerBefore(Event event, InParams in) {
        return null;
    }


    // 如果是c3p0类的连接池，一般多在线程时创建
    @Override
    public void invokerAfter(Event event, OutResult out) {
        try {
            if (out.error != null) {
            } else if (out.result instanceof Connection) {
                Connection conn = (Connection) out.result;
                out.result = connProxyConstructor.newInstance(new JdbcProxyInvocation<Connection>(conn));
            }
        } catch (Exception e) {
            logger.error("create connection Proxy fail", e);
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.empty;
    }
}
