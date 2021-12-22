package com.tl.lock;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperLock implements Watcher {
	private ZooKeeper zk;
	String root = "/tl";
	private String path;
	private String currentNode;
	private String waitNode;
	CountDownLatch latch;

	public ZookeeperLock(String host, String path) {
		this.path = path;
		try {
				zk = new ZooKeeper(host, 5000, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Stat sta = zk.exists(root, false);
				if (null == sta) {
					zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void lock() {
		try {
			currentNode = zk.create(root + "/" + path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			List<String> lockObjNodes = zk.getChildren(root, false);
			Collections.sort(lockObjNodes); //排序 //最小的那个 0 1 2 3 4
			if (currentNode.equals(root + "/" + lockObjNodes.get(0))) {
				return;

			} else {
				// lock_0001
				String childZnode = currentNode.substring(currentNode.lastIndexOf("/") + 1);//获取当前节点
				int num = Collections.binarySearch(lockObjNodes, childZnode);//当前节点去找下 看看在什么问题
				if(num==0){
					num=1;
				}
				waitNode = lockObjNodes.get(num - 1);
				Stat stat = zk.exists(root + "/" + waitNode, true);
				if (null != stat) {
					latch = new CountDownLatch(1);
					this.latch.await(5000, TimeUnit.MILLISECONDS);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void unLock() {
		try {
			zk.delete(currentNode, -1);
			currentNode=null;
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if(null!=latch){
		 latch.countDown();
		}
	}

}
