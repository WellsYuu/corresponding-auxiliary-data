package com.tuling.netty.serial.nettyprotobuf.handler;

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.tuling.netty.serial.proto.SubscribeReqProto;
import com.tuling.netty.serial.proto.SubscribeReqProto.SubscribeReq;

/**
 * 消息接受处理类
 */
public class ClientMessageHandler extends SimpleChannelHandler {

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println("server resp : " + e.getMessage());
		ChannelBuffer cb = (ChannelBuffer)e.getMessage();
		cb.array();
		
	}

	/**
	 * 新连接
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelConnected");
		for (int i = 0; i < 10; i++) {
			ctx.getChannel().write(req(i));
		}
	}

	private SubscribeReq req(int i) {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		List<String> address = new ArrayList<String>();
		address.add("长沙市岳麓区");
		address.add("深圳市南山区");

		builder.setSubReqID(i)
				.setProductName("Netty 权威指南" + i)
				.setUserName("xxxx")
				.addAllAddress(address);

		return builder.build();
	}

	/**
	 * 捕获异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("exceptionCaught");
	}

	/**
	 * 必须是链接已经建立，关闭通道的时候才会触发
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelDisconnected");
	}

	/**
	 * channel关闭的时候触发
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelClosed");
	}
}
