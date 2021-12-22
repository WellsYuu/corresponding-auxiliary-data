package com.tl.flasher.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClusterConnectionHandler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
public class JedisCluster extends redis.clients.jedis.JedisCluster implements JedisCommands {
    private int maxRedirections;
    private JedisClusterConnectionHandler connectionHandler;

    public JedisCluster(Set<HostAndPort> nodes, int timeout) {
        super(nodes, timeout);
        this.init();
    }

    public JedisCluster(Set<HostAndPort> nodes) {
        super(nodes);
        this.init();
    }

    public JedisCluster(Set<HostAndPort> nodes, int timeout, int maxRedirections) {
        super(nodes, timeout, maxRedirections);
        this.init();
    }

    public JedisCluster(Set<HostAndPort> nodes, GenericObjectPoolConfig poolConfig) {
        super(nodes, poolConfig);
        this.init();
    }

    public JedisCluster(Set<HostAndPort> nodes, int timeout, GenericObjectPoolConfig poolConfig) {
        super(nodes, timeout, poolConfig);
        this.init();
    }

    public JedisCluster(Set<HostAndPort> jedisClusterNode, int timeout, int maxRedirections, GenericObjectPoolConfig poolConfig) {
        super(jedisClusterNode, timeout, maxRedirections, poolConfig);
        this.init();
    }

    public JedisCluster(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, int maxRedirections, GenericObjectPoolConfig poolConfig) {
        super(jedisClusterNode, connectionTimeout, soTimeout, maxRedirections, poolConfig);
        this.init();
    }

    @Override
    public String set(final byte[] key, final byte[] value) {
        return new JedisClusterCommand<String>(connectionHandler, maxRedirections) {
            @Override
            public String execute(Jedis connection) {
                return connection.set(key, value);
            }
        }.run(key);
    }

    @Override
    public String set(final byte[] key, final byte[] value, final byte[] nxxx, final byte[] expx,
                      final long time) {
        return new JedisClusterCommand<String>(connectionHandler, maxRedirections) {
            @Override
            public String execute(Jedis connection) {
                return connection.set(key, value, nxxx, expx, time);
            }
        }.run(key);
    }

    @Override
    public byte[] get(final byte[] key) {
        return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
            @Override
            public byte[] execute(Jedis connection) {
                return connection.get(key);
            }
        }.run(key);
    }

    @Override
    public byte[] getSet(final byte[] key, final byte[] value) {
        return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
            @Override
            public byte[] execute(Jedis connection) {
                return connection.getSet(key, value);
            }
        }.run(key);
    }

    @Override
    public String setex(final byte[] key, final int seconds, final byte[] value) {
        return new JedisClusterCommand<String>(connectionHandler, maxRedirections) {
            @Override
            public String execute(Jedis connection) {
                return connection.setex(key, seconds, value);
            }
        }.run(key);
    }

    @Override
    public Long append(final byte[] key, final byte[] value) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.append(key, value);
            }
        }.run(key);
    }

    @Override
    public byte[] substr(final byte[] key, final int start, final int end) {
        return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
            @Override
            public byte[] execute(Jedis connection) {
                return connection.substr(key, start, end);
            }
        }.run(key);
    }

    @Override
    public Long hset(final byte[] key, final byte[] field, final byte[] value) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.hset(key, field, value);
            }
        }.run(key);
    }

    @Override
    public byte[] hget(final byte[] key, final byte[] field) {
        return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
            @Override
            public byte[] execute(Jedis connection) {
                return connection.hget(key, field);
            }
        }.run(key);
    }

    @Override
    public Long hsetnx(final byte[] key, final byte[] field, final byte[] value) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.hsetnx(key, field, value);
            }
        }.run(key);
    }

    @Override
    public String hmset(final byte[] key, final Map<byte[], byte[]> hash) {
        return new JedisClusterCommand<String>(connectionHandler, maxRedirections) {
            @Override
            public String execute(Jedis connection) {
                return connection.hmset(key, hash);
            }
        }.run(key);
    }

    @Override
    public List<byte[]> hmget(final byte[] key, final byte[]... fields) {
        return new JedisClusterCommand<List<byte[]>>(connectionHandler, maxRedirections) {
            @Override
            public List<byte[]> execute(Jedis connection) {
                return connection.hmget(key, fields);
            }
        }.run(key);
    }

    @Override
    public Long hincrBy(final byte[] key, final byte[] field, final long value) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.hincrBy(key, field, value);
            }
        }.run(key);
    }

    @Override
    public Boolean hexists(final byte[] key, final byte[] field) {
        return new JedisClusterCommand<Boolean>(connectionHandler, maxRedirections) {
            @Override
            public Boolean execute(Jedis connection) {
                return connection.hexists(key, field);
            }
        }.run(key);
    }

    @Override
    public Long hdel(final byte[] key, final byte[]... fields) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.hdel(key, fields);
            }
        }.run(key);
    }

    @Override
    public Set<byte[]> hkeys(final byte[] key) {
        return new JedisClusterCommand<Set<byte[]>>(connectionHandler, maxRedirections) {
            @Override
            public Set<byte[]> execute(Jedis connection) {
                return connection.hkeys(key);
            }
        }.run(key);
    }

    @Override
    public Map<byte[], byte[]> hgetAll(final byte[] key) {
        return new JedisClusterCommand<Map<byte[], byte[]>>(connectionHandler, maxRedirections) {
            @Override
            public Map<byte[], byte[]> execute(Jedis connection) {
                return connection.hgetAll(key);
            }
        }.run(key);
    }

    @Override
    public Long rpush(final byte[] key, final byte[]... strings) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.rpush(key, strings);
            }
        }.run(key);
    }

    @Override
    public Long lpush(final byte[] key, final byte[]... strings) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.lpush(key, strings);
            }
        }.run(key);
    }

    @Override
    public List<byte[]> lrange(final byte[] key, final long start, final long end) {
        return new JedisClusterCommand<List<byte[]>>(connectionHandler, maxRedirections) {
            @Override
            public List<byte[]> execute(Jedis connection) {
                return connection.lrange(key, start, end);
            }
        }.run(key);
    }

    @Override
    public byte[] lindex(final byte[] key, final long index) {
        return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
            @Override
            public byte[] execute(Jedis connection) {
                return connection.lindex(key, index);
            }
        }.run(key);
    }

    @Override
    public Long lrem(final byte[] key, final long count, final byte[] value) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.lrem(key, count, value);
            }
        }.run(key);
    }

    @Override
    public byte[] lpop(final byte[] key) {
        return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
            @Override
            public byte[] execute(Jedis connection) {
                return connection.lpop(key);
            }
        }.run(key);
    }

    @Override
    public byte[] rpop(final byte[] key) {
        return new JedisClusterCommand<byte[]>(connectionHandler, maxRedirections) {
            @Override
            public byte[] execute(Jedis connection) {
                return connection.rpop(key);
            }
        }.run(key);
    }

    @Override
    public Long sadd(final byte[] key, final byte[]... members) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.sadd(key, members);
            }
        }.run(key);
    }

    @Override
    public Set<byte[]> smembers(final byte[] key) {
        return new JedisClusterCommand<Set<byte[]>>(connectionHandler, maxRedirections) {
            @Override
            public Set<byte[]> execute(Jedis connection) {
                return connection.smembers(key);
            }
        }.run(key);
    }

    @Override
    public Long srem(final byte[] key, final byte[]... members) {
        return new JedisClusterCommand<Long>(connectionHandler, maxRedirections) {
            @Override
            public Long execute(Jedis connection) {
                return connection.srem(key, members);
            }
        }.run(key);
    }

    @Override
    public Boolean sismember(final byte[] key, final byte[] member) {
        return new JedisClusterCommand<Boolean>(connectionHandler, maxRedirections) {
            @Override
            public Boolean execute(Jedis connection) {
                return connection.sismember(key, member);
            }
        }.run(key);
    }


    private void init(){
        this.setMaxRedirections();
        this.setConnectionHandler();
    }
    private void setMaxRedirections() {
        try {
            Class clazz = JedisCluster.class.getSuperclass();
            Field field = clazz.getDeclaredField("maxRedirections");
            field.setAccessible(true);
            this.maxRedirections = (Integer) field.get(this);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private void setConnectionHandler() {
        try {
            Class clazz = JedisCluster.class.getSuperclass();
            Field field = clazz.getDeclaredField("connectionHandler");
            field.setAccessible(true);
            this.connectionHandler = (JedisClusterConnectionHandler) field.get(this);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
