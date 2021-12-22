package com.tuling.netty.day4.socket;

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
	// 包头,要特殊一点
	public static final int HEAD_FLAG = -32523523;
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		/*Channel channel = ctx.getChannel();
		String msg = "Hello,zhang fei";
		for (int i = 0; i < 1000; i++) {
			channel.write(msg);
		}*/
		
		Channel channel = ctx.getChannel();
		String msg = "Hello,zhang fei";
		byte[] bs = msg.getBytes();
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(HEAD_FLAG);
		buffer.writeInt(bs.length);
		buffer.writeBytes(bs);
		
		for (int i = 0; i < 1000; i++) {
			channel.write(buffer);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
	}
}
