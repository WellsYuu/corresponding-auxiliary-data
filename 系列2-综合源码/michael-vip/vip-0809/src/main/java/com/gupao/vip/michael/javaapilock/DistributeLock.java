package com.gupao.vip.michael.javaapilock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DistributeLock {


    private static final String ROOT_LOCKS="/LOCKS";//根节点

    private ZooKeeper zooKeeper;

    private int sessionTimeout; //会话超时时间

    private String lockID; //记录锁节点id

    private final static byte[] data={1,2}; //节点的数据

    private CountDownLatch countDownLatch=new CountDownLatch(1);

    public DistributeLock() throws IOException, InterruptedException {
        this.zooKeeper=ZookeeperClient.getInstance();
        this.sessionTimeout=ZookeeperClient.getSessionTimeout();
    }

    //获取锁的方法
    public boolean lock(){
        try {
            //LOCKS/00000001
            lockID=zooKeeper.create(ROOT_LOCKS+"/",data, ZooDefs.Ids.
                    OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            System.out.println(Thread.currentThread().getName()+"->成功创建了lock节点["+lockID+"], 开始去竞争锁");

            List<String> childrenNodes=zooKeeper.getChildren(ROOT_LOCKS,true);//获取根节点下的所有子节点
            //排序，从小到大
            SortedSet<String> sortedSet=new TreeSet<String>();
            for(String children:childrenNodes){
                sortedSet.add(ROOT_LOCKS+"/"+children);
            }
            String first=sortedSet.first(); //拿到最小的节点
            if(lockID.equals(first)){
                //表示当前就是最小的节点
                System.out.println(Thread.currentThread().getName()+"->成功获得锁，lock节点为:["+lockID+"]");
                return true;
            }
            SortedSet<String> lessThanLockId=sortedSet.headSet(lockID);
            if(!lessThanLockId.isEmpty()){
                String prevLockID=lessThanLockId.last();//拿到比当前LOCKID这个几点更小的上一个节点
                zooKeeper.exists(prevLockID,new LockWatcher(countDownLatch));
                countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                //上面这段代码意味着如果会话超时或者节点被删除（释放）了
                System.out.println(Thread.currentThread().getName()+" 成功获取锁：["+lockID+"]");
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlock(){
        System.out.println(Thread.currentThread().getName()+"->开始释放锁:["+lockID+"]");
        try {
            zooKeeper.delete(lockID,-1);
            System.out.println("节点["+lockID+"]成功被删除");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        final CountDownLatch latch=new CountDownLatch(10);
        Random random=new Random();
        for(int i=0;i<10;i++){
            new Thread(()->{
                DistributeLock lock=null;
                try {
                    lock=new DistributeLock();
                    latch.countDown();
                    latch.await();
                    lock.lock();
                    Thread.sleep(random.nextInt(500));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if(lock!=null){
                        lock.unlock();
                    }
                }
            }).start();
        }
    }
}
