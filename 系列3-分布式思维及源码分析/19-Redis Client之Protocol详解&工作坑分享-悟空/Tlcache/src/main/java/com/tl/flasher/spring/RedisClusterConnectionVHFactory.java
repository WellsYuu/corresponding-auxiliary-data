package com.tl.flasher.spring;

import com.tl.flasher.Constants;
import com.tl.flasher.jedis.JedisCluster;
import com.tl.flasher.zookeeper.GedisGroups;
import com.tl.flasher.zookeeper.ZkListener;

import com.google.common.collect.Sets;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
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
public class RedisClusterConnectionVHFactory implements IRedisClusterConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClusterConnectionVHFactory.class);
    
    private String hostPort;
    private int timeout = Constants.DEFAULT_CLUSTER_TIMEOUT;
    private int maxRedirections = Constants.DEFAULT_CLUSTER_MAX_REDIRECTIONS;
    private JedisPoolConfig jedisPoolConfig;
    private String business;

    private GedisGroups hostGroups;
    private JedisCluster jedisCluster;
    
    @Override
    public void destroy() throws Exception {
        if(null != jedisCluster){
            jedisCluster.close();
        }
        jedisCluster = null;

        LOGGER.info("RedisClusterConnectionVHFactory.destroy() is running!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       Assert.hasText(hostPort);
       Assert.hasText(business);
       
       hostGroups = new GedisGroups(hostPort,getBusiness());
       hostGroups.addChangeListner(new DataChangeListener());
       List<String> hostPs = hostGroups.getValues();

       Set<HostAndPort> hostAndPorts = Sets.newHashSet();
       for(String s : hostPs){
           String[] ss = s.split(":");
           hostAndPorts.add(new HostAndPort(ss[0], Integer.valueOf(ss[1])));
       }

        if(null == jedisCluster){
            if(null == jedisPoolConfig){
                jedisPoolConfig = new JedisPoolConfig();
            }
            jedisCluster = new JedisCluster(hostAndPorts,timeout,maxRedirections,jedisPoolConfig);
        }

        LOGGER.info("RedisClusterConnectionVHFactory is running!");
    }

    private void refresh(){
    	List<String> hostPs = hostGroups.getValues();
        Set<HostAndPort> hostAndPorts = Sets.newHashSet();
        for(String s : hostPs){
            String[] ss = s.split(":");
            hostAndPorts.add(new HostAndPort(ss[0], Integer.valueOf(ss[1])));
        }

        JedisCluster newjedisCluster = new JedisCluster(hostAndPorts,timeout,maxRedirections,jedisPoolConfig);
        jedisCluster = newjedisCluster;

        LOGGER.info("RedisClusterConnectionVHFactory.refresh() running!");
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
    
    private class DataChangeListener implements ZkListener{

		@Override
		public void dataEvent(WatchedEvent event) {
			// TODO Auto-generated method stub
			if(event.getType() == EventType.NodeChildrenChanged || event.getType() == EventType.NodeDataChanged){
				refresh();
			}
			
		}
    }
    
}
