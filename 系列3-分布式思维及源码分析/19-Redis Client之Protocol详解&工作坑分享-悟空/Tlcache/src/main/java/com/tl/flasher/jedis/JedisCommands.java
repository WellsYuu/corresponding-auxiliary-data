package com.tl.flasher.jedis;

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
 * 图灵学院悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */
public interface JedisCommands extends redis.clients.jedis.JedisCommands {
    String set(byte[] key, byte[] value);

    byte[] get(byte[] key);

    String set(final byte[] key, final byte[] value, final byte[] nxxx, final byte[] expx,
               final long time);

    byte[] getSet(final byte[] key, final byte[] value);

    String setex(final byte[] key, final int seconds, final byte[] value);

    Long append(final byte[] key, final byte[] value);

    byte[] substr(final byte[] key, final int start, final int end);

    Long hset(final byte[] key, final byte[] field, final byte[] value);

    byte[] hget(final byte[] key, final byte[] field);

    Long hsetnx(final byte[] key, final byte[] field, final byte[] value);

    String hmset(final byte[] key, final Map<byte[], byte[]> hash);

    List<byte[]> hmget(final byte[] key, final byte[]... fields);

    Long hincrBy(final byte[] key, final byte[] field, final long value);

    Boolean hexists(final byte[] key, final byte[] field);

    Long hdel(final byte[] key, final byte[]... fields);

    Set<byte[]> hkeys(final byte[] key);

    Map<byte[], byte[]> hgetAll(final byte[] key);

    Long rpush(final byte[] key, final byte[]... strings);

    Long lpush(final byte[] key, final byte[]... strings);

    List<byte[]> lrange(final byte[] key, final long start, final long end);

    byte[] lindex(final byte[] key, final long index);

    Long lrem(final byte[] key, final long count, final byte[] value);

    byte[] lpop(final byte[] key);

    byte[] rpop(final byte[] key);

    Long sadd(final byte[] key, final byte[]... members);

    Set<byte[]> smembers(final byte[] key);

    Long srem(final byte[] key, final byte[]... members);

    Boolean sismember(final byte[] key, final byte[] member);

}
