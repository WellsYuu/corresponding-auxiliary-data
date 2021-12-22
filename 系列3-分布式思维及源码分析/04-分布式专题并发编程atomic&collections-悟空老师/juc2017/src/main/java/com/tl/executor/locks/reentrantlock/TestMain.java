package com.tl.executor.locks.reentrantlock;/*
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
 * 以往视频加小乔老师QQ：895900009
 * 悟空老师QQ：245553999
 */

import java.util.Iterator;
import java.util.Set;

public class TestMain {

    public static void main(String[] args) {

        SearchServer searchServer = new SearchServer();

        searchServer.put("湖南","湖南");
        searchServer.put("湖北","湖北");
        searchServer.put("北京","北京");
        searchServer.put("河北","河北");
        searchServer.put("河南","河南");
        Set<String> set= (Set) searchServer.search("北", 10);

         for(Iterator iterator= set.iterator();iterator.hasNext();){
             System.out.println(iterator.next());

         }

        
    }
}
