package com.tuling.netty.day4.nettysolution;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder;
import org.jboss.netty.handler.codec.frame.LineBasedFrameDecoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
/**
 * 服务端
 * @author 张飞老师
 */
public class Server {
	public static void main(String[] args) throws Exception {
		// 服务类
		ServerBootstrap bootstrap = new ServerBootstrap();

		// boss线程，主要监听端口和获取worker线程及分配socketChannel给worker线程
		ExecutorService boss = Executors.newCachedThreadPool();
		// worker线程负责数据读写
		ExecutorService worker = Executors.newCachedThreadPool();

		// 设置niosocket工厂
		bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

		// 设置管道的工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				// 管道过滤器
				// 方案1：消息定长
				//pipeline.addLast("fixedLength", new FixedLengthFrameDecoder(18));
				
				// 方案2：行分隔符
				//pipeline.addLast("fixedLength", new LineBasedFrameDecoder(1024));
				
				// 方案3：自定义特殊符号进行分割
				pipeline.addLast("delimiter", new DelimiterBasedFrameDecoder(1024, 
						ChannelBuffers.copiedBuffer("#@#".getBytes())));
				pipeline.addLast("1",new StringDecoder());	
				pipeline.addLast("2",new ServerMessageHandler());		
				return pipeline;
			}
		});

		// 服务类绑定端口
		bootstrap.bind(new InetSocketAddress(7777));

		System.out.println("服务端启动...");

	}
	
	
}