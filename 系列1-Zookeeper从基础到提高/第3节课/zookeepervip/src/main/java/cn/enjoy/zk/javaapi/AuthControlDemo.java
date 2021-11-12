package cn.enjoy.zk.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class AuthControlDemo implements Watcher{
    private final static String CONNECTSTRING="192.168.30.10:2181";
    private static CountDownLatch countDownLatch=new CountDownLatch(1);
    private static CountDownLatch countDownLatch2=new CountDownLatch(1);

    private static ZooKeeper zookeeper;
    private static Stat stat=new Stat();
    public static void main(String[] args) throws Exception {
        zookeeper=new ZooKeeper(CONNECTSTRING, 5000, new AuthControlDemo());
        countDownLatch.await();

        ACL acl=new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("root:root")));
        ACL acl2=new ACL(ZooDefs.Perms.CREATE, new Id("ip","192.168.1.1"));

        List<ACL> acls=new ArrayList<>();
        acls.add(acl);
        acls.add(acl2);
        zookeeper.create("/auth1","123".getBytes(),acls,CreateMode.PERSISTENT);
        zookeeper.addAuthInfo("digest","root:root".getBytes());

        zookeeper.create("/auth1/auth1-1","123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.EPHEMERAL);


        ZooKeeper zooKeeper1=new ZooKeeper(CONNECTSTRING, 5000, new AuthControlDemo());
        countDownLatch.await();
        zooKeeper1.addAuthInfo("digest","root:root".getBytes());
        zooKeeper1.delete("/auth1/auth1-1",-1);


        // acl (create /delete /admin /read/write)
        //权限模式： ip/Digest（username:password）/world/super

    }
    public void process(WatchedEvent watchedEvent) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
            if(Event.EventType.None==watchedEvent.getType()&&null==watchedEvent.getPath()){
                countDownLatch.countDown();
                System.out.println(watchedEvent.getState()+"-->"+watchedEvent.getType());
            }
        }

    }
}
