package cn.enjoy.product.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by VULCAN on 2018/7/28.
 */
public class ServiceRegister {

    private  static final String BASE_SERVICES = "/services";
    private static final String  SERVICE_NAME="/products";

    public static  void register(String address,int port) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",5000,(watchedEvent)->{});
            Stat exists = zooKeeper.exists(BASE_SERVICES + SERVICE_NAME, false);
            if(exists==null) {
                zooKeeper.create(BASE_SERVICES + SERVICE_NAME,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String server_path = address+":"+port;
            zooKeeper.create(BASE_SERVICES + SERVICE_NAME+"/child",server_path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
