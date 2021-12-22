package com.tuling.netty.day4.nettysolution;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
/**
 * 服务端消息处理类
 * @author 张飞老师
 */
public class ServerMessageHandler extends SimpleChannelHandler {
	
	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println("receive request: " + e.getMessage());
	}
	
	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
	}
}

