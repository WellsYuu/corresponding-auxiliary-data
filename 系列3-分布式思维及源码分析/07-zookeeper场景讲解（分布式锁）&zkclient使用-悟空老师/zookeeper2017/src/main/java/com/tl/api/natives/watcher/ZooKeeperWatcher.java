package com.tl.api.natives.watcher;


import com.tl.ZookeeperUtil;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ZooKeeperWatcher implements Watcher {

	/** 定义原子变量 */
	AtomicInteger seq = new AtomicInteger();
	/** zk父路径设置 */
	private static final String PARENT_PATH = ZookeeperUtil.path;
	/** zk子路径设置 */
	private static final String CHILDREN_PATH =PARENT_PATH+"/children";
	/** zk变量 */
	private ZooKeeper zk = null;



	public ZooKeeperWatcher() throws IOException {
		zk = new ZooKeeper(ZookeeperUtil.connectString, ZookeeperUtil.sessionTimeout, this);
	}





	/***
	 * 创建持久类型
	 * @param path
	 * @param data
	 * @return
	 * @see ZooKeeperMain
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String addPersistent(String path,String data) throws KeeperException, InterruptedException {
		return  zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

	}



	/***
	 * 更新信息
	 * @param path
	 * @see ZooKeeperMain#processZKCmd
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String getData(String path) throws KeeperException, InterruptedException {
		byte data[] = zk.getData(path,false,null);
		data = (data == null)? "null".getBytes() : data;
		return new String(data);

	}

	/***
	 * 更新信息
	 * @param path
	 * @see ZooKeeperMain#processZKCmd
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String getData(String path,boolean watch) throws KeeperException, InterruptedException {
		byte data[] = zk.getData(path,watch,null);
		data = (data == null)? "null".getBytes() : data;
		return new String(data);

	}

	/***
	 * 更新信息
	 * @param path
	 * @param data
	 * @see ZooKeeperMain#processZKCmd
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public Stat setData(String path,String data) throws KeeperException, InterruptedException {
		return zk.setData(path, data.getBytes(), -1);
		//byte[] data = zk.getData("path", false, null);
		//System.out.println(new String(data));
	}

	/***
	 * 是否存在
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public Stat exists(String path) throws KeeperException, InterruptedException {
		return zk.exists(path,false);

	}

	/***
	 * 是否存在
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public Stat exists(String path,boolean watch) throws KeeperException, InterruptedException {
		return zk.exists(path,watch);

	}

	public  List<String> getChildren(String path,Watcher watcher) throws KeeperException, InterruptedException {
		return zk.getChildren(path,watcher);
	}



	public  List<String> getChildren(String path,boolean watcher) throws KeeperException, InterruptedException {
		return zk.getChildren(path,watcher);
	}


	/***
	 * 删除
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void delete(String path) throws KeeperException, InterruptedException {
		zk.delete(path,-1);
	}
	/***
	 * 删除
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void deleteRecursive(String path) throws KeeperException, InterruptedException {
		ZKUtil.deleteRecursive(zk, path);
	}


	/**
	 * 关闭ZK连接
	 */
	public void close() throws InterruptedException {
	   this.zk.close();
	}

	
	/**
	 * Watcher通知回调方法
	 */
	@Override
	public void process(WatchedEvent event) {
		
		System.out.println("process>event = " + event);
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (event == null) {
			return;
		}
		// 连接状态
		KeeperState keeperState = event.getState();
		// 事件类型
		EventType eventType = event.getType();
		// 受影响的path
		String path = event.getPath();
		String num = "[收到Watcher:" + this.seq.incrementAndGet() + "通知]";
		System.out.println(num + "连接状态:\t" + keeperState.toString());
		System.out.println(num + "事件类型:\t" + eventType.toString());
		if (KeeperState.SyncConnected == keeperState) {
/*			// 成功连接上ZK服务器
			if (EventType.None == eventType) {
				System.out.println(logPrefix + "成功连接上ZK服务器");
				//connectedSemaphore.countDown();
			}
			//创建节点
			else */
			if (EventType.NodeCreated == eventType) {
				System.out.println(num + "节点创建");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					this.exists(path,true);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			//更新节点
			else if (EventType.NodeDataChanged == eventType) {
				System.out.println(num + "节点数据更新");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					System.out.println(num + "数据内容: " + this.getData(PARENT_PATH));
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			//更新子节点
			else if (EventType.NodeChildrenChanged == eventType) {
//				通知之后的业务
				System.out.println(num + "子节点变更");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					System.out.println(num + "子节点列表：" + this.getChildren(PARENT_PATH, null));
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			//删除节点
			else if (EventType.NodeDeleted == eventType) {
				System.out.println(num + "节点 " + path + " 被删除");
			}
			else ;
		} 
		else if (KeeperState.Disconnected == keeperState) {
			System.out.println(num + "与ZK服务器断开连接");
		} 
		else if (KeeperState.AuthFailed == keeperState) {
			System.out.println(num + "权限检查失败");
		} 
		else if (KeeperState.Expired == keeperState) {
			System.out.println(num + "会话失效");
		}
		else ;
		System.out.println("-----------------------");

	}



}