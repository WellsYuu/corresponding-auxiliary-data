package com.tl.flasher.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClusterConnectionHandler;
import redis.clients.jedis.JedisSlotBasedConnectionHandler;
import redis.clients.jedis.exceptions.*;
import redis.clients.util.JedisClusterCRC16;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */
public abstract class JedisClusterCommand<T> extends redis.clients.jedis.JedisClusterCommand<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterCommand.class);

    private JedisClusterConnectionHandler connectionHandler;
    private int redirections;
    private ThreadLocal<Jedis> askConnection;

    public JedisClusterCommand(JedisClusterConnectionHandler connectionHandler, int maxRedirections) {
        super(connectionHandler, maxRedirections);
        this.init();
    }

    public T run(byte[] key) {
        if (key == null) {
            throw new JedisClusterException("No way to dispatch this command to Redis Cluster.");
        }

        return runWithRetries(key, this.redirections, false, false);
    }

    private T runWithRetries(byte[] key, int redirections, boolean tryRandomNode, boolean asking) {
        if (redirections <= 0) {
            throw new JedisClusterMaxRedirectionsException("Too many Cluster redirections?");
        }

        Jedis connection = null;
        try {

            if (asking) {
                // TODO: Pipeline asking with the original command to make it
                // faster....
                connection = askConnection.get();
                connection.asking();

                // if asking success, reset asking flag
                asking = false;
            } else {
                if (tryRandomNode) {
//                    connection = connectionHandler.getConnection();
                    connection = (Jedis)reflectConnectionHandler(JedisSlotBasedConnectionHandler.class, "getConnection");
                } else {
//                    connection = connectionHandler.getConnectionFromSlot(JedisClusterCRC16.getCRC16(key));
                    connection = (Jedis)reflectConnectionHandler(JedisSlotBasedConnectionHandler.class, "getConnectionFromSlot",JedisClusterCRC16.getCRC16(key));
                }
            }

            return execute(connection);
        } catch (JedisConnectionException jce) {
            if (tryRandomNode) {
                // maybe all connection is down
                throw jce;
            }

            // release current connection before recursion
            releaseConnection(connection);
            connection = null;

            // retry with random connection
            return runWithRetries(key, redirections - 1, true, asking);
        } catch (JedisRedirectionException jre) {
            // release current connection before recursion or renewing
            releaseConnection(connection);
            connection = null;

            if (jre instanceof JedisAskDataException) {
                asking = true;
                askConnection.set(this.connectionHandler.getConnectionFromNode(jre.getTargetNode()));
            } else if (jre instanceof JedisMovedDataException) {
                // it rebuilds cluster's slot cache
                // recommended by Redis cluster specification
                this.connectionHandler.renewSlotCache();
            } else {
                throw new JedisClusterException(jre);
            }

            return runWithRetries(key, redirections - 1, false, asking);
        } finally {
            releaseConnection(connection);
        }

    }

    private void releaseConnection(Jedis connection) {
        if (connection != null) {
            connection.close();
        }
    }

    private Object reflectConnectionHandler(Class clazz, String methodName, Object... params){
        int paramsSize = params.length;
        Class[] types = new Class[paramsSize];
        for(int i=0;i<paramsSize;i++){
            types[i] = params[i].getClass();
        }

        Method method = null;
        try {
            method = clazz.getMethod(methodName,types);
        } catch (NoSuchMethodException e) {
            for(int i=0;i<paramsSize;i++){
                Class type = params[i].getClass();
                if(type == Integer.class){
                    type = Integer.TYPE;
                } else if(type == Long.class){
                    type = Long.TYPE;
                } else if(type == Float.class){
                    type = Float.TYPE;
                } else if(type == Double.class){
                    type = Double.TYPE;
                } else if(type == Boolean.class){
                    type = Boolean.TYPE;
                } else if(type == Byte.class){
                    type = Byte.TYPE;
                } else if(type == Short.class){
                    type = Short.TYPE;
                } else if(type == Character.class){
                    type = Character.TYPE;
                }
                types[i] = type;
            }
            try {
                method = clazz.getMethod(methodName, types);
            } catch (NoSuchMethodException e1) {
                LOGGER.info("JedisClusterCommand.reflectConnectionHandler is error", e1);
            }
        }
        FastClass fastClass = FastClass.create(clazz);
        FastMethod fastMethod = fastClass.getMethod(method);
        try {
            return fastMethod.invoke(this.connectionHandler, params);
        } catch (InvocationTargetException e) {
            LOGGER.info("JedisClusterCommand.reflectConnectionHandler is error", e);
        }
        return null;
    }

    private void init(){
        this.setMaxRedirections();
        this.setConnectionHandler();
        this.setAskConnection();
    }
    private void setMaxRedirections() {
        try {
            Class clazz = JedisClusterCommand.class.getSuperclass();
            Field field = clazz.getDeclaredField("redirections");
            field.setAccessible(true);
            this.redirections = (Integer) field.get(this);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private void setConnectionHandler() {
        try {
            Class clazz = JedisClusterCommand.class.getSuperclass();
            Field field = clazz.getDeclaredField("connectionHandler");
            field.setAccessible(true);
            this.connectionHandler = (JedisClusterConnectionHandler) field.get(this);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private void setAskConnection() {
        try {
            Class clazz = JedisClusterCommand.class.getSuperclass();
            Field field = clazz.getDeclaredField("askConnection");
            field.setAccessible(true);
            this.askConnection = (ThreadLocal<Jedis>) field.get(this);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
