package com.tl.protocol;/*
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

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.List;

public class Crc16Sharding {
    List<RedisClient> pool;

    public Crc16Sharding(List<RedisClient> pool) {
        this.pool = pool;
    }

    /**
     * 通过一个key可以定位到一块 节点
     * @param key
     * @return
     */
    public RedisClient crc16(String key){

        int num=Math.abs(key.hashCode()%pool.size());
        return  pool.get(num);
       /* if(key.length()<3){
           return pool.get(0);
        }else  if(key.length()<6){
            return  pool.get(1);
        }else{
            return  pool.get(2);
        }*/

    }



}
