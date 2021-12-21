package com.cbt.agent.collects.jdbc;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;
import com.cbt.agent.collect.*;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConnectionHandle implements CollectHandle {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDriverHandle.class);

    private static Constructor<?> psProxyConstructor;

    static {
        try {
            psProxyConstructor = getProxyConstructor(PreparedStatement.class);
        } catch (Exception e) {
            logger.error("JdbcDriverHandle initialize fail", e);
        }
    }

    private static Constructor<?> getProxyConstructor(Class<?> interfaces) throws NoSuchMethodException, SecurityException {
        Class<?> clas = Proxy.getProxyClass(JdbcConnectionHandle.class.getClassLoader(), interfaces);
        Constructor<?> cons = clas.getConstructor(InvocationHandler.class);
        cons.setAccessible(true);
        return cons;
    }

    public JdbcConnectionHandle() {
        Assert.notNull(psProxyConstructor);
    }

    @Override
    public Event invokerBefore(Event event ,InParams in) {
        if (TraceContext.getDefault().getCurrentSession() == null)
            return null;
        if(in.methodName.equals("prepareStatement")){
            return prepareStatmentBefore(in);
        }
        return null;
    }

    private Event prepareStatmentBefore(InParams in) {
        Event newEvent=new Event(getEventType(),true);// 发起一个跟踪事件，并设置事件销毁方法
        newEvent.setDestoryMethod("java.sql.PreparedStatement","close",null);
        JdbcData jdbcData = new JdbcData();
        jdbcData.setBeginTime(System.currentTimeMillis());
        newEvent.setData(jdbcData);
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        TraceNode node = new TraceNode();
        node.setTraceId(session.getTraceId());
        node.setRpcId(session.getNextRpcId());
        newEvent.setData(new Object[]{jdbcData,node});

        return  newEvent;
    }

    private void prepareStatementAfter(Event event, OutResult out){
        PreparedStatement ps = (PreparedStatement) out.result;
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        Object[] eventData= (Object[]) event.getData();
        JdbcData data = (JdbcData) eventData[0];
        TraceNode node= (TraceNode) eventData[1];
        data.setSql((String) out.args[0]);
        try {
            data.setUrl(ps.getConnection().getMetaData().getURL());
            data.setUserName(ps.getConnection().getMetaData().getUserName());
            if (out.error != null) {
                data.setError(out.error);
            } else {
                JdbcProxyInvocation<PreparedStatement> jdbcProxy = new JdbcProxyInvocation<PreparedStatement>(ps);
                jdbcProxy.setEvent(event);
                out.result = psProxyConstructor.newInstance(jdbcProxy);
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("create PreparedStatement Proxy fail", e);
        } catch (SQLException e) {
            data.setError(e);
        }
        if (data.getError() != null) {
            JdbcData.TraceNodeBuilder.builderNode(data, node);
            session.addNode(node);
            // 提前关闭并清理事件
            event.close();
            session.clearEvent(event);
        }
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        if(TraceContext.getDefault().getCurrentSession()==null){
            return;
        }else if (event == null || !event.isActive())
            return;
        if (out.methodName.equals("prepareStatement")) {
            prepareStatementAfter(event, out);
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.JDBC;
    }



}
