package com.tuling.netty.day4.custom;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 客户端消息处理类
 * @author 张飞
 */
public class ClientHandler extends SimpleChannelHandler {
	// 包头
	private static final int HEAD_FLAG = -32323231;
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Channel channel = ctx.getChannel();
		String msg = "Hello,zhang fei";
		byte[] bytes = msg.getBytes();
		// 定义数据包 ，结构为：包头 + 长度 + 数据
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		// 1.写包头
		buffer.writeInt(HEAD_FLAG);// 4字节
		// 2.写长度
		buffer.writeInt(bytes.length);// 4字节
		// 3.写数据本身
		buffer.writeBytes(bytes);
		
		for (int i = 0; i < 1000; i++) {
			channel.write(buffer);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
	}
}
