package com.tuling.netty.day4.custom;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringEncoder;
/**
 * 客户端
 * 
 * @author 张飞
 */
public class Client {

	public static void main(String[] args) throws Exception {
		
		//服务类
		ClientBootstrap bootstrap = new  ClientBootstrap();
		//线程池
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		//socket工厂
		bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));
		//管道工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("1", new StringEncoder());
				pipeline.addLast("2", new ClientHandler());
				return pipeline;
			}
		});
		
		//连接服务端
		bootstrap.connect(new InetSocketAddress("127.0.0.1", 7777)).sync();
	}

}
