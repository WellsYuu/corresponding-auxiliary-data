package com.tuling.netty.serial.nettyprotobuf.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.tuling.netty.serial.proto.SubscribeReqProto;
import com.tuling.netty.serial.proto.SubscribeRespProto;
import com.tuling.netty.serial.proto.SubscribeRespProto.SubscribeResp;

public class ServerMessageHandler extends SimpleChannelHandler {
	private SubscribeResp resp(int subReqID){
		SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqID(subReqID)
				.setRespCode("0")
				.setDesc("Netty 权威指南订购成功 !");
		return builder.build();
	}
	
	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)e.getMessage();
		System.out.println("client req: "+ req);
		ctx.getChannel().write(resp(req.getSubReqID()));		
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("exceptionCaught");
	}

	/**
	 * 获取新连接事件
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelConnected");
	}

	/**
	 * 关闭通道的时候触发 （必须是链接已经建立）
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelDisconnected");
	}

	/**
	 * 通道关闭的时候触发
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelClosed");
	}
}

