package com.cbt.agent.collects.jdbc;

import com.cbt.agent.collect.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * 
 * Description: <br/>
 * 以动态代理的方式对jdbc 进行切入，执行逻辑等同于字节插码插的逻辑执行。与
 * {@linkplain AgentSourceManager#buildTraceSrc} 方法当中逻辑一至<br/>
 * 支持Connection 、PreparedStatement、及 Statement 接口的切入<br/>
 * 
 * @author zengguangwei@cbt.com
 * @date: 2016年6月6日 下午7:24:48
 * @version 1.0
 * @since JDK 1.7
 */
public class JdbcProxyInvocation<T> implements InvocationHandler {

    private Class<T> targetClass;
    private T target;
    private CollectHandle handle;
    private List<String> targetMethods;
    private Event event;

    @SuppressWarnings("unchecked")
    public JdbcProxyInvocation(Connection connection) {
        target = (T) connection;
        targetClass = (Class<T>) Connection.class;
        handle = CollectHandleBeanFactory.getBean(JdbcConnectionHandle.class);
    }

    @SuppressWarnings("unchecked")
    public JdbcProxyInvocation(PreparedStatement preparedStatement) {
        target = (T) preparedStatement;
        targetClass = (Class<T>) PreparedStatement.class;
        handle = CollectHandleBeanFactory.getBean(JdbcPreparedStatementHandle.class);
    }

    @SuppressWarnings("unchecked")
    public JdbcProxyInvocation(Statement statement) {
        target = (T) statement;
        targetClass = (Class<T>) Statement.class;
        handle = CollectHandleBeanFactory.getBean(JdbcStatementHandle.class);
    }
    
  

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        InParams _inParams = new InParams();
        OutResult _outResult = new OutResult();
        _inParams.className = targetClass.getName();
        _inParams.methodName = method.getName();
        _outResult.className = targetClass.getName();
        _outResult.methodName = method.getName();
        _inParams.args = args;
        _outResult.args = args;
        // 临时实现 ,后面需要通过统一的event 传递路径进行优化
        _outResult.others.put("JdbcEvent", event);
        Event newEvent = handle.invokerBefore(event, _inParams);
        try {
            _outResult.result = method.invoke(target, args);
        } catch (final InvocationTargetException e) {
            if (e.getTargetException() != null) {
                _outResult.error = e.getTargetException();
                throw e.getTargetException();
            } else {
                _outResult.error = e;
                throw e;
            }
        } catch (Throwable e) {
            _outResult.error = e;
            throw e;
        } finally {
            handle.invokerAfter(newEvent != null ? newEvent : event, _outResult);
        }
        return _outResult.result;
    }

}
