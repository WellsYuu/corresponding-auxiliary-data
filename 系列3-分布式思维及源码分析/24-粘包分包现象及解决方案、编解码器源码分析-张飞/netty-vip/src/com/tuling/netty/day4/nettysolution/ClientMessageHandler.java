package com.tuling.netty.day4.nettysolution;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 客户端消息接受处理类
 * @author 张飞
 */
public class ClientMessageHandler extends SimpleChannelHandler {

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println("server response : " + e.getMessage());
	}

	/**
	 * 新连接
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Channel channel = ctx.getChannel();
		String separator = "#@#";//System.getProperty("line.separator");// 系统换行符
		String msg = "zhang fei send cmd";
		for (int i = 0; i < 1000; i++) {
			channel.write(msg + i + separator);
		}
	}
	
	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
	}
}
