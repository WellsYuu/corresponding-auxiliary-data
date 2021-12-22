package com.tl.executor.collections.map;/*
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapExample {

    public static void main(String[] args) {
        /* Map<String,String> map=new HashMap<String,String>();
         map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        map.put("4","4");
        //map.keySet().iterator();
        Iterator<String> it=  map.keySet().iterator();

        while(it.hasNext()){
            String key=it.next();
            System.out.println(it.next());
            if(key.equals("3")){
                map.put(key+"key","3");
            }


        }*/

        Map<String,String>   map=new ConcurrentHashMap<String, String>();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        map.put("4","4");
        //map.keySet().iterator();
        Iterator<String>   it=  map.keySet().iterator();

        while(it.hasNext()){
            String key=it.next();
            System.out.println(it.next());
            if(key.equals("3")){
                map.put(key+"key","3");
            }


        }



    }
}
