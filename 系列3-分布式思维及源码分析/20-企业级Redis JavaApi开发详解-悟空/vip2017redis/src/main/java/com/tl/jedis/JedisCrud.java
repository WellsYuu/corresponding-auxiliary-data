package com.tl.jedis;/*
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

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class JedisCrud {


    private static Jedis jedis = new Jedis("192.168.0.12",6379);


    @Test
    public void testKey() throws InterruptedException
    {
        System.out.println("清空数据："+jedis.flushDB());
        System.out.println("判断某个键是否存在："+jedis.exists("username"));
        System.out.println("新增<'username','wukong'>的键值对："+jedis.set("username", "wukong"));
        System.out.println("是否存在:"+jedis.exists("name"));
        System.out.println("新增<'password','password'>的键值对："+jedis.set("password", "password"));
        Set<String> keys = jedis.keys("*");
        System.out.println("系统中所有的键如下："+keys);
        System.out.println("删除键password:"+jedis.del("password"));
        System.out.println("判断键password是否存在："+jedis.exists("password"));
        System.out.println("设置键username的过期时间为5s:"+jedis.expire("username", 5));
        TimeUnit.SECONDS.sleep(2);
        System.out.println("查看键username的剩余生存时间："+jedis.ttl("username"));
        System.out.println("移除键username的生存时间："+jedis.persist("username"));
        System.out.println("查看键username的剩余生存时间："+jedis.ttl("username"));
        System.out.println("查看键username所存储的值的类型："+jedis.type("username"));
    }

    /***
     * 字符串操作
     * memcached和redis同样有append的操作，但是memcached有prepend的操作，redis中并没有。
     * @throws InterruptedException
     */
    @Test
    public void testString() throws InterruptedException
    {
        jedis.flushDB();
        System.out.println("===========增加数据===========");
        System.out.println(jedis.set("key1","value1"));
        System.out.println(jedis.set("key2","value2"));
        System.out.println(jedis.set("key3", "value3"));
        System.out.println("删除键key2:"+jedis.del("key2"));
        System.out.println("获取键key2:"+jedis.get("key2"));
        System.out.println("修改key1:"+jedis.set("key1", "value1Changed"));
        System.out.println("获取key1的值："+jedis.get("key1"));
        System.out.println("在key3后面加入值："+jedis.append("key3", "End"));
        System.out.println("key3的值："+jedis.get("key3"));
        System.out.println("增加多个键值对："+jedis.mset("key01","value01","key02","value02","key03","value03"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03","key04"));
        System.out.println("删除多个键值对："+jedis.del(new String[]{"key01","key02"}));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));

        jedis.flushDB();
        System.out.println("===========新增键值对防止覆盖原先值==============");
        System.out.println(jedis.setnx("key1", "value1"));
        System.out.println(jedis.setnx("key2", "value2"));
        System.out.println(jedis.setnx("key2", "value2-new"));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("key2"));

        System.out.println("===========新增键值对并设置有效时间=============");
        System.out.println(jedis.setex("key3", 2, "value3"));
        System.out.println(jedis.get("key3"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println(jedis.get("key3"));

        System.out.println("===========获取原值，更新为新值==========");//GETSET is an atomic set this value and return the old value command.
        System.out.println(jedis.getSet("key2", "key2GetSet"));
        System.out.println(jedis.get("key2"));

        System.out.println("获得key2的值的字串："+jedis.getrange("key2", 2, 4));
    }

    /***
     * 整数和浮点数
     */
    @Test
    public void testNumber()
    {
        jedis.flushDB();
        jedis.set("key1", "1");
        jedis.set("key2", "2");
        jedis.set("key3", "2.3");
        System.out.println("key1的值："+jedis.get("key1"));
        System.out.println("key2的值："+jedis.get("key2"));
        System.out.println("key1的值加1："+jedis.incr("key1"));
        System.out.println("获取key1的值："+jedis.get("key1"));
        System.out.println("key2的值减1："+jedis.decr("key2"));
        System.out.println("获取key2的值："+jedis.get("key2"));
        System.out.println("将key1的值加上整数5："+jedis.incrBy("key1", 5));
        System.out.println("获取key1的值："+jedis.get("key1"));
        System.out.println("将key2的值减去整数5："+jedis.decrBy("key2", 5));
        System.out.println("获取key2的值："+jedis.get("key2"));
    }

    /***
     * 列表
     */
    @Test
    public void testList()
    {
        jedis.flushDB();
        System.out.println("===========添加一个list===========");
        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
        jedis.lpush("collections", "HashSet");
        jedis.lpush("collections", "TreeSet");
        jedis.lpush("collections", "TreeMap");
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));//-1代表倒数第一个元素，-2代表倒数第二个元素
        System.out.println("collections区间0-3的元素："+jedis.lrange("collections",0,3));
        System.out.println("===============================");
        // 删除列表指定的值 ，第二个参数为删除的个数（有重复时），后add进去的值先被删，类似于出栈
        System.out.println("删除指定元素个数："+jedis.lrem("collections", 2, "HashMap"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("删除下表0-3区间之外的元素："+jedis.ltrim("collections", 0, 3));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（左端）："+jedis.lpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections添加元素，从列表右端，与lpush相对应："+jedis.rpush("collections", "EnumMap"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（右端）："+jedis.rpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("修改collections指定下标1的内容："+jedis.lset("collections", 1, "LinkedArrayList"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("===============================");
        System.out.println("collections的长度："+jedis.llen("collections"));
        System.out.println("获取collections下标为2的元素："+jedis.lindex("collections", 2));
        System.out.println("===============================");
        jedis.lpush("sortedList", "3","6","2","0","7","4");
        System.out.println("sortedList排序前："+jedis.lrange("sortedList", 0, -1));
        System.out.println(jedis.sort("sortedList"));
        System.out.println("sortedList排序后："+jedis.lrange("sortedList", 0, -1));
    }

    /***
     * set集合
     */
    @Test
    public void testSet()
    {
        jedis.flushDB();
        System.out.println("============向集合中添加元素============");
        System.out.println(jedis.sadd("eleSet", "e1","e2","e4","e3","e0","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("删除一个元素e0："+jedis.srem("eleSet", "e0"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("删除两个元素e7和e6："+jedis.srem("eleSet", "e7","e6"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
        System.out.println("随机的移除集合中的一个元素："+jedis.spop("eleSet"));
        System.out.println("eleSet的所有元素为："+jedis.smembers("eleSet"));
        System.out.println("eleSet中包含元素的个数："+jedis.scard("eleSet"));
        System.out.println("e3是否在eleSet中："+jedis.sismember("eleSet", "e3"));
        System.out.println("e1是否在eleSet中："+jedis.sismember("eleSet", "e1"));
        System.out.println("e1是否在eleSet中："+jedis.sismember("eleSet", "e5"));
        System.out.println("=================================");
        System.out.println(jedis.sadd("eleSet1", "e1","e2","e4","e3","e0","e8","e7","e5"));
        System.out.println(jedis.sadd("eleSet2", "e1","e2","e4","e3","e0","e8"));
        System.out.println("将eleSet1中删除e1并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e1"));
        System.out.println("将eleSet1中删除e2并存入eleSet3中："+jedis.smove("eleSet1", "eleSet3", "e2"));
        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
        System.out.println("eleSet3中的元素："+jedis.smembers("eleSet3"));
        System.out.println("============集合运算=================");
        System.out.println("eleSet1中的元素："+jedis.smembers("eleSet1"));
        System.out.println("eleSet2中的元素："+jedis.smembers("eleSet2"));
        System.out.println("eleSet1和eleSet2的交集:"+jedis.sinter("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的并集:"+jedis.sunion("eleSet1","eleSet2"));
        System.out.println("eleSet1和eleSet2的差集:"+jedis.sdiff("eleSet1","eleSet2"));//eleSet1中有，eleSet2中没有
    }

    /***
     * 散列
     */
    @Test
    public void testHash()
    {
        jedis.flushDB();
        Map<String,String> map = new HashMap<String,String>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");
        jedis.hmset("hash",map);
        jedis.hset("hash", "key5", "value5");
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));//return Map<String,String>
        System.out.println("散列hash的所有键为："+jedis.hkeys("hash"));//return Set<String>
        System.out.println("散列hash的所有值为："+jedis.hvals("hash"));//return List<String>
        System.out.println("将key6保存的值加上一个整数，如果key6不存在则添加key6："+jedis.hincrBy("hash", "key6", 6));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("将key6保存的值加上一个整数，如果key6不存在则添加key6："+jedis.hincrBy("hash", "key6", 3));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("删除一个或者多个键值对："+jedis.hdel("hash", "key2"));
        System.out.println("散列hash的所有键值对为："+jedis.hgetAll("hash"));
        System.out.println("散列hash中键值对的个数："+jedis.hlen("hash"));
        System.out.println("判断hash中是否存在key2："+jedis.hexists("hash","key2"));
        System.out.println("判断hash中是否存在key3："+jedis.hexists("hash","key3"));
        System.out.println("获取hash中的值："+jedis.hmget("hash","key3"));
        System.out.println("获取hash中的值："+jedis.hmget("hash","key3","key4"));
    }

    @Test
    public void testSortedSet()
    {
        jedis.flushDB();
        Map<String,Double> map = new HashMap<String,Double>();
        map.put("key2",1.2);
        map.put("key3",4.0);
        map.put("key4",5.0);
        map.put("key5",0.2);
        System.out.println(jedis.zadd("zset", 3,"key1"));
        System.out.println(jedis.zadd("zset",map));
        System.out.println("zset中的所有元素："+jedis.zrange("zset", 0, -1));
        System.out.println("zset中的所有元素："+jedis.zrangeWithScores("zset", 0, -1));
        System.out.println("zset中的所有元素："+jedis.zrangeByScore("zset", 0,100));
        System.out.println("zset中的所有元素："+jedis.zrangeByScoreWithScores("zset", 0,100));
        System.out.println("zset中key2的分值："+jedis.zscore("zset", "key2"));
        System.out.println("zset中key2的排名："+jedis.zrank("zset", "key2"));
        System.out.println("删除zset中的元素key3："+jedis.zrem("zset", "key3"));
        System.out.println("zset中的所有元素："+jedis.zrange("zset", 0, -1));
        System.out.println("zset中元素的个数："+jedis.zcard("zset"));
        System.out.println("zset中分值在1-4之间的元素的个数："+jedis.zcount("zset", 1, 4));
        System.out.println("key2的分值加上5："+jedis.zincrby("zset", 5, "key2"));
        System.out.println("key3的分值加上4："+jedis.zincrby("zset", 4, "key3"));
        System.out.println("zset中的所有元素："+jedis.zrange("zset", 0, -1));
    }

    @Test
    public void testSort()
    {
        jedis.flushDB();
        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        SortingParams sortingParameters = new SortingParams();
        System.out.println(jedis.sort("collections",sortingParameters.alpha()));
        System.out.println("===============================");
        jedis.lpush("sortedList", "3","6","2","0","7","4");
        System.out.println("sortedList排序前："+jedis.lrange("sortedList", 0, -1));
        System.out.println("升序："+jedis.sort("sortedList", sortingParameters.asc()));
        System.out.println("升序："+jedis.sort("sortedList", sortingParameters.desc()));
        System.out.println("===============================");
        jedis.lpush("userlist", "33");
        jedis.lpush("userlist", "22");
        jedis.lpush("userlist", "55");
        jedis.lpush("userlist", "11");
        jedis.hset("user:66", "name", "66");
        jedis.hset("user:55", "name", "55");
        jedis.hset("user:33", "name", "33");
        jedis.hset("user:22", "name", "79");
        jedis.hset("user:11", "name", "24");
        jedis.hset("user:11", "add", "beijing");
        jedis.hset("user:22", "add", "shanghai");
        jedis.hset("user:33", "add", "guangzhou");
        jedis.hset("user:55", "add", "chongqing");
        jedis.hset("user:66", "add", "xi'an");
        sortingParameters = new SortingParams();
        sortingParameters.get("user:*->name");
        sortingParameters.get("user:*->add");
        System.out.println(jedis.sort("userlist",sortingParameters));
    }


}
