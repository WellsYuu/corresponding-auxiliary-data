package com.jiagouedu.core.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;

/**
 * redis cache provider
 * @author wukong 图灵学院 QQ:245553999
 */
public class RedisCacheProvider implements CacheProvider {
    private RedisTemplate<String, Serializable> redisTemplate;

    public RedisTemplate<String, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(final String key, final Serializable cacheObject) {
        redisTemplate.execute(new RedisCallback<Serializable>() {
            @Override
            public Serializable doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<Serializable> value = (RedisSerializer<Serializable>) redisTemplate.getValueSerializer();
                connection.set(redisTemplate.getStringSerializer().serialize(key), value.serialize(cacheObject));
                return null;
            }
        });
    }

    @Override
    public Serializable get(final String key) {
        return redisTemplate.execute(new RedisCallback<Serializable>() {
            @Override
            public Serializable doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(redisKey)) {
                    byte[] value = connection.get(redisKey);
                    Serializable valueSerial = (Serializable)redisTemplate.getValueSerializer()
                            .deserialize(value);
                    return valueSerial;
                }
                return null;
            }
        });
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void clear() {

    }
}
