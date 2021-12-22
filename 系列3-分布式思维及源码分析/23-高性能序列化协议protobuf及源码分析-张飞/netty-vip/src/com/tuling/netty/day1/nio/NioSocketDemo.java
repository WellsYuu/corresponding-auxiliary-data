package com.tuling.netty.day1.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * nio
 */
public class NioSocketDemo {
	// 通道管理器（选择器），多个用户共用的，所以需要放到这里
	private Selector selector;
	
	/**
	 * 初始化服务端ServerSocketChannel通道，并初始化选择器
	 * 获得一个ServerSocket通道，并对该通道做一些初始化的工作
	 */
	public void initServer(int port) throws IOException {
		// 获取ServerSocket通道 ， 相对于传统的ServerSocket
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 设置通道为非阻塞
		serverChannel.configureBlocking(false);
		// 将该通道对应的ServerSocket绑定到port端口
		serverChannel.socket().bind(new InetSocketAddress(port));
		// 获得一个通道选择器(管理器)
		this.selector = Selector.open();
		// 将通道选择器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
		// 当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
		// 意思是大门交给selector看着，给我监听是否有accpet事件
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务端启动成功...");
		/*
		***SelectionKey中定义的4中事件 ***
		OP_ACCEPT —— 接收连接继续事件，表示服务器监听到了客户连接，服务器可以接收这个连接了
		OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
		OP_READ —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
		OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）
		*/
		
	}

	/**
	 * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
	 */
	public void listenSelector() throws IOException {
		// 轮询访问selector
		while (true) {
			// 当注册的事件到达时，方法返回；否则,该方法会一直阻塞
			// 多路复用  Reactor模型
			this.selector.select();
			// 无论是否有读写事件发生，selector每隔1s被唤醒一次  
			 //this.selector.select(1000);
			 //this.selector.selectNow();
			// 获得selector中选中的项的迭代器，选中的项为注册的事件
			Iterator<?> iteratorKey = this.selector.selectedKeys().iterator();
			while (iteratorKey.hasNext()) {
				SelectionKey selectionKey = (SelectionKey) iteratorKey.next();
				// 删除已选的key,以防重复处理
				iteratorKey.remove();
				// 处理请求
				handler(selectionKey);
			}
		}
	}

	/**
	 * 处理请求
	 */
	public void handler(SelectionKey selectionKey) throws IOException {
		if (selectionKey.isAcceptable()) {//处理客户端连接请求事件
			System.out.println("新的客户端连接...");
			ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
			// 获得和客户端连接的通道
			// 完成该操作意味着完成TCP三次握手，TCP物理链路正式建立  
			SocketChannel channel = server.accept();
			// 设置成非阻塞
			channel.configureBlocking(false);
			// 在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
			channel.register(this.selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {// 处理读的事件
			// 服务器可读取消息:得到事件发生的Socket通道
			SocketChannel channel = (SocketChannel) selectionKey.channel();
			// 创建读取的缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024);//1kb
			int readData = channel.read(buffer);
			if(readData > 0){
				String msg = new String(buffer.array(),"GBK").trim();// 先讲缓冲区数据转化成byte数组,再转化成String
				System.out.println("服务端收到信息：" + msg);
				
				//回写数据
				ByteBuffer writeBackBuffer = ByteBuffer.wrap("receive data".getBytes("GBK"));
				channel.write(writeBackBuffer);// 将消息回送给客户端
			}else{
				System.out.println("客户端关闭咯...");
				//SelectionKey对象会失效，这意味着Selector再也不会监控与它相关的事件
				selectionKey.cancel();
			}
		}
	}
	/**
	 * 启动服务端测试
	 */
	public static void main(String[] args) throws IOException {
		NioSocketDemo server = new NioSocketDemo();
		// 初始化服务端
		server.initServer(8888);
		// 服务器端监听Selector事件
		server.listenSelector();
	}
}
