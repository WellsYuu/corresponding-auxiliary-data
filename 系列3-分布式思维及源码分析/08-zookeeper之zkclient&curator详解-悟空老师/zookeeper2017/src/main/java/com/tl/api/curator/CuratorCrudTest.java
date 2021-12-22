package com.tl.api.curator;/*
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

import java.util.List;

public class CuratorCrudTest {

    public static void main(String[] args) {
        String path="/root";
        CuratorCrud curatorCrud=new CuratorCrud();
        if(null!=curatorCrud.checkExists(path)) {
            curatorCrud.delete(path);
            System.out.println("---删除---");
        }
        //curatorCrud.create(path,"hi".getBytes());
        //root/bbb/ccc hi
       curatorCrud.create(path+"/bbb"+"/ccc","hi".getBytes());//递归创建 可以支持赋值
       List<String> list= curatorCrud.getChildren("/");//获取path下面的节点
        for(String chipath:list){
            System.out.println(chipath);
        }
       curatorCrud.setData(path,"hello".getBytes());
       System.out.println(new String(curatorCrud.getData(path)));

    }
}
