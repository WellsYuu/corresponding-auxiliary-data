package com.tl.flasher.spring;

import com.tl.flasher.Constants;
import com.tl.flasher.jedis.JedisCluster;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;
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
public class RedisClusterConnectionFactory implements IRedisClusterConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClusterConnectionFactory.class);

    private Set<String> hostPorts;
    private String hostPort;
    private int timeout = Constants.DEFAULT_CLUSTER_TIMEOUT;
    private int maxRedirections = Constants.DEFAULT_CLUSTER_MAX_REDIRECTIONS;
    private JedisPoolConfig jedisPoolConfig;
    private String business;

    private JedisCluster jedisCluster;

    @Override
    public void destroy() throws Exception {
        if(null != jedisCluster){
            jedisCluster.close();
        }
        jedisCluster = null;

        LOGGER.info("RedisClusterConnectionFactory.destroy() is running!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> hostAndPorts = Sets.newHashSet();
        if(null != hostPorts && !hostPorts.isEmpty()){
            for(String s : hostPorts){
                String[] ss = s.split(":");
                hostAndPorts.add(new HostAndPort(ss[0], Integer.valueOf(ss[1])));
            }
        } else if(null != hostPort  && !"".equals(hostPort)) {
            String[] hostPs = hostPort.split(",");
            for(String s : hostPs){
                String[] ss = s.split(":");
                hostAndPorts.add(new HostAndPort(ss[0], Integer.valueOf(ss[1])));
            }
        } else {
            throw new RuntimeException("hostPorts OR hostPort is null or empty");
        }

        if(null == jedisCluster){
            if(null == jedisPoolConfig){
                jedisPoolConfig = new JedisPoolConfig();
            }
            jedisCluster = new JedisCluster(hostAndPorts,timeout,maxRedirections,jedisPoolConfig);
        }

        LOGGER.info("RedisClusterConnectionFactory is running!");
    }

    public Set<String> getHostPorts() {
        return hostPorts;
    }

    public void setHostPorts(Set<String> hostPorts) {
        this.hostPorts = hostPorts;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxRedirections() {
        return maxRedirections;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }
}
