package com.imooc.jiangzh.user;

import com.imooc.jiangzh.ConnectonHelper;
import com.imooc.jiangzh.user.vo.UserModel;
import net.rubyeye.xmemcached.*;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

public class ShowApi {

    static MemcachedClient client = ConnectonHelper.getClient();

    // 新增【set,add】
    public static void showAdd(UserModel um) throws Exception {
        MemcachedClient memcachedClient = ConnectonHelper.getClient();

        memcachedClient.set("set-user:"+um.getUuid(),3600,um);

        memcachedClient.add("add-user:"+um.getUuid(),3600,um);

    }

    // 修改【replace,append,prepend】
    public static void showUpdate(UserModel um) throws Exception{
        MemcachedClient memcachedClient = ConnectonHelper.getClient();

        memcachedClient.replace("set-user:"+um.getUuid(),3600,um);

        // Hi! imooc jiangzh
        memcachedClient.prepend("k1","Hi! ");
        memcachedClient.append("k1"," jiangzh");

    }

    // 删除【delete】

    // 查询【get/gets】
    public static void showQuery(String key)throws  Exception{
        MemcachedClient memcachedClient = ConnectonHelper.getClient();

        UserModel um = memcachedClient.get(key);
        System.out.println("get方法获取的值="+um);
    }

    public static  GetsResponse<UserModel> showGets(String key)throws  Exception{
        MemcachedClient memcachedClient = ConnectonHelper.getClient();

        GetsResponse<UserModel> gets = memcachedClient.gets(key);

        return gets;
    }
    // 检查更新【cas】
    public static void showCAS(UserModel um)throws  Exception{
        String key = "set-user:" + um.getUuid();
        // 第一件事先获取版本号
        GetsResponse<UserModel> userModelGetsResponse = showGets(key);
        long cas = userModelGetsResponse.getCas();

        // 演示cas版本是否生效
        UserModel um1 = new UserModel(1,"imooc admin",20);
        showUpdate(um1);

        // 问题：
        /*
            背景： 订单来了【10个电脑】， 检查库存【11个电脑】，修改库存数量，发货
            -> order
            ->  int num = checkWareHouse(...) -> 11  select
              -> 时间差
            ->  update
         */

        // 进行更新操作
        MemcachedClient memcachedClient = ConnectonHelper.getClient();
        boolean isSuccess = memcachedClient.cas(key, 3600, um, cas);

    }

    @Test
    public void showXMCAS() throws  Exception {

        // 操作XMemcached提供的CAS操作
        client.cas("k1", new CASOperation<String>() {
            // 重试次数
            public int getMaxTries() {
                return Integer.MAX_VALUE;
            }
            // 修改内容
            public String getNewValue(long cas, String currentValue) {
                return "Hi ! "+currentValue + "！！！！！";
            }
        });

    }

    // 获取所有的key列表
    @Test
    public void showKeyIterator() throws  Exception{
        KeyIterator keyIterator = client.getKeyIterator(AddrUtil.getOneAddress("192.168.1.18:2222"));
        while(keyIterator.hasNext()){
            System.out.println("keys="+keyIterator.next());
        }
    }


    // 数值操作【incr/decr】
    public static void showNumChange()throws  Exception{
        /*
            long result = memcachedClient.incr("k5",5,10);
            result1 = 10  -> API【如果key不存在，则第三个参数为初始值】

            result = memcachedClient.incr("k5",40,10);
            result2 = 50  -> 如果key存在，则进行递增或递减操作

            result = memcachedClient.decr("k5",25,10);
            result3 = 25  -> 如果key存在，则进行递增或递减操作

            result = memcachedClient.decr("k5",30,10);
            result4 = 0   -> decr是不能减出负数
         */

        MemcachedClient memcachedClient = ConnectonHelper.getClient();

        long result = memcachedClient.incr("k5",5,10);
        System.out.println("result1 = "+result);

        result = memcachedClient.incr("k5",40,10);
        System.out.println("result2 = "+result);

        result = memcachedClient.decr("k5",25,10);
        System.out.println("result3 = "+result);

        result = memcachedClient.decr("k5",30,10);
        System.out.println("result4 = "+result);
    }

    // XMemcached提供的计数器
    @Test
    public void showCounter() throws Exception {

        Counter counter = new Counter(client,"k5",10);
        long c1 = counter.incrementAndGet();
        System.out.println("c1="+c1);
        long c2 = counter.decrementAndGet();
        System.out.println("c2="+c2);
        long c3 = counter.addAndGet(88);
        System.out.println("c3="+c3);
        long c4 = counter.addAndGet(-10000);
        System.out.println("c4="+c4);
        counter.set(50);
        long c5 = counter.get();
        System.out.println("c5="+c5);
        String key = counter.getKey();
        System.out.println("key="+key);

    }

    @Test
    public void showTouch() throws  Exception {

        // 更新数据过期时间

       /*
        1、先存入一条数据 【设置过过期时间 10个小时】
        2、先获取待更新过期时间的数据
        3、再通过 replace | set 方法，将数据修改回去，同时设置过期时间
        */

       /*
        1、先存入一条数据 【设置过过期时间 10个小时】
        2、直接使用touch进行更新过期时间
        */
        client.set("k1",3600,"Hello imooc");
        client.touch("k1",10);
    }

    // 演示命名空间
    @Test
    public void showNameSpace() throws  Exception {
        String ns1 = "ns1";
        String ns2 = "ns2";
        // 赋值操作
        client.withNamespace(ns1, new MemcachedClientCallable<String>() {
            public String call(MemcachedClient memcachedClient) throws MemcachedException, InterruptedException, TimeoutException {

                memcachedClient.set("k10",0," Hello jiangzh!! ");

                return null;
            }
        });

        String str = client.get("k10");
        System.out.println("str="+str);

        String k10 = client.withNamespace(ns1, new MemcachedClientCallable<String>() {
            public String call(MemcachedClient memcachedClient) throws MemcachedException, InterruptedException, TimeoutException {
                return memcachedClient.get("k10");
            }
        });

        System.out.println("k10="+k10);

        String k102 = client.withNamespace(ns2, new MemcachedClientCallable<String>() {
            public String call(MemcachedClient memcachedClient) throws MemcachedException, InterruptedException, TimeoutException {
                return memcachedClient.get("k10");
            }
        });

        System.out.println("k102="+k102);

    }


}
