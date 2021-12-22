package com.tl.executor.collections.list;/*
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArrayLists {

    public static void main(String[] args) {
        Set list=new CopyOnWriteArraySet();//ArrayList有异常
        list.add("1");
        list.add("1");
        list.add("3");
        list.add("4");
        Iterator<String> iterator= list.iterator();
        while(iterator.hasNext()){
            String key= iterator.next();
            System.out.println(key);
            if(key.equals("3")){
                list.add("5");
                //list.remove("1");
               // list.set(1,"2");
            }


        }

    }

}
