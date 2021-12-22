package com.tl.flasher.spring;

import com.tl.flasher.Constants;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

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
 * 图灵学院悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */
public class RedisConnectionFactory implements IRedisConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConnectionFactory.class);

    private String hostPort;
    private int connectionTimeout = Constants.DEFAULT_CLUSTER_TIMEOUT;
    private int soTimeout = Constants.DEFAULT_CLUSTER_TIMEOUT;
    private String business;

    private Jedis jedis;
    @Override
    public void destroy() throws Exception {
        if(null != jedis){
            jedis.close();
        }
        jedis = null;
        LOGGER.info("RedisConnectionFactory.destroy() is running!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(Strings.isNullOrEmpty(hostPort)){
            throw new RuntimeException("hostPort is null or empty");
        }
        if(null == jedis){
            String[] hostPorts = hostPort.split(":");
            jedis = new Jedis(hostPorts[0],Integer.valueOf(hostPorts[1]),connectionTimeout,soTimeout);
        }

        LOGGER.info("RedisConnectionFactory is running!");
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
