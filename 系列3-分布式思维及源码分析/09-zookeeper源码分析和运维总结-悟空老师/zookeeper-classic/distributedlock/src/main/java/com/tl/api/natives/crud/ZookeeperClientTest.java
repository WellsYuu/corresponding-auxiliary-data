package com.tl.api.natives.crud;/*
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



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZookeeperClientTest {
    final static Logger logger = LoggerFactory.getLogger(ZookeeperClientTest.class);

    public static void main(String[] args) throws Exception {
        ZookeeperClient zookeeperClient=new ZookeeperClient();
        String path="/wukong";
       // if(null==zookeeperClient.exists(path)) {
           // zookeeperClient.addPersistent(path, "是只猴子");
           // System.out.println(zookeeperClient.getData(path));
        /******************序列化***************/
        User user=new User();
        user.setUserid(1);
        user.setUserName("悟空");
        zookeeperClient.addPersistent(path, ZkSerializable.objectToByte(user));
        user= (User) ZkSerializable.byteToObject(zookeeperClient.getData2(path));//序列化
        System.out.println(user.getUserName());
        //}
      //  zookeeperClient.setData(path,"是只公猴子");
        System.out.println(zookeeperClient.getData(path));
        System.out.println(zookeeperClient.getChildren("/wukong",null));
      //  zookeeperClient.delete(path);
        System.out.println(zookeeperClient.getData(path));//删除之后会报错 可以换成 zookeeperClient.exists(path)
        zookeeperClient.close();
    }


}
