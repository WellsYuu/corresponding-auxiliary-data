package com.tl.flasher.impl;

import com.tl.flasher.Command;
import com.tl.flasher.IRedis;
import com.tl.flasher.enums.ExistEnum;
import com.tl.flasher.enums.ExpireTimeEnum;
import com.tl.flasher.spring.IRedisConnection;
import com.tl.flasher.utils.RedisUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zyh on 2015/6/29.
 */
public class RedisImpl implements IRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisImpl.class);

    private IRedisConnection redisConnectionFactory;
    private RedisImpl(){}

    public RedisImpl(IRedisConnection redisConnectionFactory){
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public IRedisConnection getRedisConnectionFactory() {
        return redisConnectionFactory;
    }
    public void setRedisConnectionFactory(IRedisConnection redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public String hget(String business, String key, String field) {
        return getJedis().hget(RedisUtil.buildKey(getBusiness(business), key), field);
    }

    @Override
    public Map<String, String> hgetAll(String business, String key) {
        return getJedis().hgetAll(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long expire(String business, String key, int seconds) {
        return getJedis().expire(RedisUtil.buildKey(getBusiness(business), key), seconds);
    }

    @Override
    public Long expireAt(String business, String key, long unixTime) {
        return getJedis().expireAt(RedisUtil.buildKey(getBusiness(business), key), unixTime);
    }

    @Override
    public Long ttl(String business, String key) {
        return getJedis().ttl(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long decrBy(String business, String key, long integer) {
        return getJedis().decrBy(RedisUtil.buildKey(getBusiness(business), key), integer);
    }

    @Override
    public Long decr(String business, String key) {
        return getJedis().decr(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long incrBy(String business, String key, long integer) {
        return getJedis().incrBy(RedisUtil.buildKey(getBusiness(business), key), integer);
    }

    @Override
    public Long incr(String business, String key) {
        return getJedis().incr(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long hset(String business, String key, String field, String value) {
        return getJedis().hset(RedisUtil.buildKey(getBusiness(business), key), field, value);
    }

    @Override
    public String set(String business, String key, String value) {
        return getJedis().set(RedisUtil.buildKey(getBusiness(business), key), value);
    }

    @Override
    public String set(String business, byte[] key, byte[] value) {
        return getJedis().set(RedisUtil.buildKey(getBusiness(business), key), value);
    }

    @Override
    public String get(String business, String key) {
        return getJedis().get(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public byte[] get(String business, byte[] key) {
        return getJedis().get(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Boolean exists(String business, String key) {
        return getJedis().exists(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public String type(String business, String key) {
        return getJedis().type(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long append(String business, String key, String value) {
        return getJedis().append(RedisUtil.buildKey(getBusiness(business), key), value);
    }

    @Override
    public String substr(String business, String key, int start, int end) {
        return getJedis().substr(RedisUtil.buildKey(getBusiness(business), key), start, end);
    }

    @Override
    public Long hsetnx(String business, String key, String field, String value) {
        return getJedis().hsetnx(RedisUtil.buildKey(getBusiness(business), key), field, value);
    }

    @Override
    public String hmset(String business, String key, Map<String, String> hash) {
        return getJedis().hmset(RedisUtil.buildKey(getBusiness(business), key), hash);
    }

    @Override
    public List<String> hmget(String business, String key, String... fields) {
        return getJedis().hmget(RedisUtil.buildKey(getBusiness(business), key), fields);
    }

    @Override
    public Long hincrBy(String business, String key, String field, long value) {
        return getJedis().hincrBy(RedisUtil.buildKey(getBusiness(business), key), field, value);
    }

    @Override
    public Boolean hexists(String business, String key, String field) {
        return getJedis().hexists(RedisUtil.buildKey(getBusiness(business), key), field);
    }

    @Override
    public Long hdel(String business, String key, String... field) {
        return getJedis().hdel(RedisUtil.buildKey(getBusiness(business), key), field);
    }

    @Override
    public Long hlen(String business, String key) {
        return getJedis().hlen(RedisUtil.buildKey(getBusiness(business),key));
    }

    @Override
    public Set<String> hkeys(String business, String key) {
        return getJedis().hkeys(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public List<Object> mutliExecute(String business, List<Command> commands) {
        String tmpBusiness = getBusiness(business);
        if(Strings.isNullOrEmpty(tmpBusiness)){
            return null;
        }
        List<Object> result = Lists.newArrayList();
        Class clazz = RedisImpl.class;
        for(Command c : commands){
            List<Object> params = c.getParameters();
            int paramsSize = params.size();
            Class[] types = new Class[paramsSize+1];
            Object[] paramss = new Object[paramsSize+1];
            paramss[0] = tmpBusiness;
            types[0] = tmpBusiness.getClass();
            for(int i=0;i<paramsSize;i++){
                Object param = params.get(i);
                types[i+1] = param.getClass();
                paramss[i+1] = param;
            }
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(c.getCommandName().name(), types);
            } catch (NoSuchMethodException e) {
                /**
                 * 做8种基本类型的转换
                 * 注：一般情况，要求反射的方法的参数，要么全部为基本数据类型，要么全部为对象数据类型，不允许两种并存
                 */
                for(int i=0;i<paramsSize;i++){
                    Object param = params.get(i);
                    Class type = param.getClass();
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
                    types[i+1] = type;
                    paramss[i+1] = param;
                }
                try {
                    method = clazz.getDeclaredMethod(c.getCommandName().name(), types);
                } catch (NoSuchMethodException e1) {
                    LOGGER.info("RedisImpl.mutliExecute is error", e1);
                }
            }
            FastClass fastClass = FastClass.create(clazz);
            FastMethod fastMethod = fastClass.getMethod(method);
            try {
                result.add(fastMethod.invoke(this, paramss));
            } catch (InvocationTargetException e) {
                LOGGER.info("RedisImpl.mutliExecute is error", e);
            }
        }
        return result;
    }

    @Override
    public long del(String business, String... keys) {
        return getJedis().del(RedisUtil.buildKeys(getBusiness(business), keys));
    }

    @Override
    public Long llen(String business, String key) {
        return getJedis().llen(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public String lpop(String business, String key) {
        return getJedis().lpop(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long lpush(String business, String key, String... values) {
        return getJedis().lpush(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public Long lrem(String business, String key, Long count, String value) {
        return getJedis().lrem(RedisUtil.buildKey(getBusiness(business), key), count, value);
    }

    @Override
    public List<String> lrange(String business, String key, Long start, Long end) {
        return getJedis().lrange(RedisUtil.buildKey(getBusiness(business), key), start, end);
    }

    @Override
    public String lindex(String business, String key, Long index) {
        return getJedis().lindex(RedisUtil.buildKey(getBusiness(business), key), index);
    }

    @Override
    public String rpop(String business, String key) {
        return getJedis().rpop(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long rpush(String business, String key, String... values) {
        return getJedis().rpush(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public Long sadd(String business, String key, String... values) {
        return getJedis().sadd(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public Long scard(String business, String key) {
        return getJedis().scard(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public String setex(String business, String key, Integer seconds, String value) {
        return getJedis().setex(RedisUtil.buildKey(getBusiness(business), key), seconds, value);
    }

    @Override
    public Set<String> smembers(String business, String key) {
        return getJedis().smembers(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long srem(String business, String key, String... values) {
        return getJedis().srem(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public String ltrim(String business, String key, Long start, Long end) {
        return getJedis().ltrim(RedisUtil.buildKey(getBusiness(business),key),start,end);
    }

    @Override
    public Boolean sismember(String business, String key, String member) {
        return getJedis().sismember(RedisUtil.buildKey(getBusiness(business),key),member);
    }

//    @Override
//    public Set<String> sinter(String business, String... keys) {
//        return getJedis().sinter(RedisUtil.buildKeys(getBusiness(business), keys));
//    }
//
//    @Override
//    public Set<String> sunion(String business, String... keys) {
//        return getJedis().sunion(RedisUtil.buildKeys(getBusiness(business), keys));
//    }
//
//    @Override
//    public Set<String> sdiff(String business, String... keys) {
//        return getJedis().sdiff(RedisUtil.buildKeys(getBusiness(business), keys));
//    }

    @Override
    public String set(String business, String key, String value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time) {
        return getJedis().set(RedisUtil.buildKey(getBusiness(business), key), value, existEnum.name(), existEnum.name(), time);
    }

    @Override
    public String set(String business, byte[] key, byte[] value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time) {
        return getJedis().set(RedisUtil.buildKey(getBusiness(business), key), value, existEnum.name().getBytes(), existEnum.name().getBytes(), time);
    }

    @Override
    public String getSet(String business, String key, String value) {
        return getJedis().getSet(RedisUtil.buildKey(getBusiness(business),key), value);
    }

    @Override
    public byte[] getSet(String business, byte[] key, byte[] value) {
        return getJedis().getSet(RedisUtil.buildKey(getBusiness(business), key), value);
    }

    @Override
    public Long append(String business, byte[] key, byte[] value) {
        return getJedis().append(RedisUtil.buildKey(getBusiness(business), key), value);
    }

    @Override
    public byte[] substr(String business, byte[] key, int start, int end) {
        return getJedis().substr(RedisUtil.buildKey(getBusiness(business), key), start, end);
    }

    @Override
    public Long hset(String business, byte[] key, byte[] field, byte[] value) {
        return getJedis().hset(RedisUtil.buildKey(getBusiness(business), key), field, value);
    }

    @Override
    public byte[] hget(String business, byte[] key, byte[] field) {
        return getJedis().hget(RedisUtil.buildKey(getBusiness(business), key), field);
    }

    @Override
    public Long hsetnx(String business, byte[] key, byte[] field, byte[] value) {
        return getJedis().hsetnx(RedisUtil.buildKey(getBusiness(business), key), field, value);
    }

    @Override
    public String hmset(String business, byte[] key, Map<byte[], byte[]> hash) {
        return getJedis().hmset(RedisUtil.buildKey(getBusiness(business), key), hash);
    }

    @Override
    public List<byte[]> hmget(String business, byte[] key, byte[]... fields) {
        return getJedis().hmget(RedisUtil.buildKey(getBusiness(business), key), fields);
    }

    @Override
    public Long hincrBy(String business, byte[] key, byte[] field, long value) {
        return getJedis().hincrBy(RedisUtil.buildKey(getBusiness(business), key), field, value);
    }

    @Override
    public Boolean hexists(String business, byte[] key, byte[] field) {
        return getJedis().hexists(RedisUtil.buildKey(getBusiness(business), key), field);
    }

    @Override
    public Long hdel(String business, byte[] key, byte[]... fields) {
        return getJedis().hdel(RedisUtil.buildKey(getBusiness(business), key), fields);
    }

    @Override
    public Set<byte[]> hkeys(String business, byte[] key) {
        return getJedis().hkeys(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Map<byte[], byte[]> hgetAll(String business, byte[] key) {
        return getJedis().hgetAll(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long rpush(String business, byte[] key, byte[]... values) {
        return getJedis().rpush(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public Long lpush(String business, byte[] key, byte[]... values) {
        return getJedis().lpush(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public List<byte[]> lrange(String business, byte[] key, Long start, Long end) {
        return getJedis().lrange(RedisUtil.buildKey(getBusiness(business), key), start, end);
    }

    @Override
    public byte[] lindex(String business, byte[] key, long index) {
        return getJedis().lindex(RedisUtil.buildKey(getBusiness(business), key), index);
    }

    @Override
    public Long lrem(String business, byte[] key, long count, byte[] value) {
        return getJedis().lrem(RedisUtil.buildKey(getBusiness(business), key), count, value);
    }

    @Override
    public byte[] lpop(String business, byte[] key) {
        return getJedis().lpop(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public byte[] rpop(String business, byte[] key) {
        return getJedis().rpop(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long sadd(String business, byte[] key, byte[]... values) {
        return getJedis().sadd(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public Set<byte[]> smembers(String business, byte[] key) {
        return getJedis().smembers(RedisUtil.buildKey(getBusiness(business), key));
    }

    @Override
    public Long srem(String business, byte[] key, byte[]... values) {
        return getJedis().srem(RedisUtil.buildKey(getBusiness(business), key), values);
    }

    @Override
    public Boolean sismember(String business, byte[] key, byte[] member) {
        return getJedis().sismember(RedisUtil.buildKey(getBusiness(business), key), member);
    }


    private String getBusiness(String business){
        if(Strings.isNullOrEmpty(getRedisConnectionFactory().getBusiness())){
            return business;
        } else {
            return getRedisConnectionFactory().getBusiness();
        }
    }

    private Jedis getJedis(){
        return getRedisConnectionFactory().getJedis();
    }
}
