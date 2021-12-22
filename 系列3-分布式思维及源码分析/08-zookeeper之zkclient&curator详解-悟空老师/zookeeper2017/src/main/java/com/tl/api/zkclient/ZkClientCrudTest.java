package com.tl.api.zkclient;/*
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

import com.tl.api.natives.crud.User;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZkClientCrudTest {
    final static Logger logger = LoggerFactory.getLogger(ZkClientCrudTest.class);
    public static void main(String[] args) {
        ZkClientCrud<User> zkClientCrud=new ZkClientCrud<User>(new SerializableSerializer());
        String path="/root";
        zkClientCrud.deleteRecursive(path);
        zkClientCrud.createPersistent(path,"hi");
     /*  zkClientCrud.createPersistent(path+"/a/b/c",true);//递归创建 但是不能设在value
       //zkClientCrud.createPersistent(path,"hi");
        logger.info(zkClientCrud.readData(path));
        //更新
        zkClientCrud.writeData(path,"hello");
        logger.info(zkClientCrud.readData(path));
        logger.info(String.valueOf(zkClientCrud.getChildren(path)));
        //子节点
        List<String> list=zkClientCrud.getChildren(path);
        for(String child:list){
            logger.info("子节点:"+child);
        }*/

        User user=new User();
        user.setUserid(1);
        user.setUserName("悟空");
        zkClientCrud.writeData(path,user);
        System.out.println(zkClientCrud.readData(path).getUserName());;


    }



}
