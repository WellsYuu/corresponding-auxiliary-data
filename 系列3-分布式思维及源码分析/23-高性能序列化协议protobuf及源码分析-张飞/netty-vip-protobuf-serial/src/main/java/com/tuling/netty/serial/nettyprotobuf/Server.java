package com.tuling.netty.serial.nettyprotobuf;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.tuling.netty.serial.nettyprotobuf.handler.ServerMessageHandler;
import com.tuling.netty.serial.proto.SubscribeReqProto;

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
				pipeline.addLast("1",new ProtobufVarint32FrameDecoder());					
				pipeline.addLast("2",new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));					
				pipeline.addLast("3",new ProtobufVarint32LengthFieldPrepender());					
				pipeline.addLast("4",new ProtobufEncoder());						
				pipeline.addLast("5",new ServerMessageHandler());		
				return pipeline;
			}
		});

		// 服务类绑定端口
		bootstrap.bind(new InetSocketAddress(7777));

		System.out.println("服务端启动...");

	}
	
	
}