package com.tuling.netty.serial.nettyprotobuf;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.jboss.netty.handler.codec.string.StringDecoder;

import com.tuling.netty.serial.nettyprotobuf.handler.ClientMessageHandler;
import com.tuling.netty.serial.proto.SubscribeRespProto;
/**
 * netty客户端入门
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
				pipeline.addLast("", new StringDecoder());
//				pipeline.addLast("1", new ProtobufVarint32FrameDecoder());
//				pipeline.addLast("2", new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
//				pipeline.addLast("3", new ProtobufVarint32LengthFieldPrepender());
//				pipeline.addLast("4", new ProtobufEncoder());
				pipeline.addLast("5", new ClientMessageHandler());
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
