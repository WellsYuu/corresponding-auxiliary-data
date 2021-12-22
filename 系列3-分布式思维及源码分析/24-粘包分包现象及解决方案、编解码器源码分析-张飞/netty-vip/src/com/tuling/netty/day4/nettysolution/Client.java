package com.tuling.netty.day4.nettysolution;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder;
import org.jboss.netty.handler.codec.frame.LineBasedFrameDecoder;
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
				// 方案1：消息定长
				//pipeline.addLast("fixedLength", new FixedLengthFrameDecoder(18));
				// 方案2：行分隔符
				//pipeline.addLast("fixedLength", new LineBasedFrameDecoder(1024));
				// 方案3：自定义特殊符号进行分割
				pipeline.addLast("delimiter", new DelimiterBasedFrameDecoder(1024, 
						ChannelBuffers.copiedBuffer("#@#".getBytes())));
				pipeline.addLast("1",new StringEncoder());
				pipeline.addLast("2", new ClientMessageHandler());
				return pipeline;
			}
		});
		
		//连接服务端
		@SuppressWarnings("unused")
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 7777)).sync();
		
		
//		Channel channel = connect.getChannel();
//		System.out.println("client start");
		
//		Scanner scanner = new Scanner(System.in);
//		while(true){
//			System.out.println("请输入:");
//			channel.write(scanner.next());
//		}
	}

}
