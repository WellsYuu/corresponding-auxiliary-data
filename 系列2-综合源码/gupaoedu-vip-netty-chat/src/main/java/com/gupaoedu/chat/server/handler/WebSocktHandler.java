package com.gupaoedu.chat.server.handler;

import org.apache.log4j.Logger;

import com.gupaoedu.chat.processor.MsgProcessor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public class WebSocktHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private static Logger LOG = Logger.getLogger(WebSocktHandler.class);
	
	private MsgProcessor processor = new MsgProcessor();
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,TextWebSocketFrame msg) throws Exception {
		processor.sendMsg(ctx.channel(), msg.text());
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		Channel client = ctx.channel();
		String addr = processor.getAddress(client);
		LOG.info("WebSocket Client:" + addr + "加入");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel client = ctx.channel();
		processor.logout(client);
		LOG.info("WebSocket Client:" + processor.getNickName(client) + "离开");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel client = ctx.channel();
		String addr = processor.getAddress(client);
		LOG.info("WebSocket Client:" + addr + "上线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel client = ctx.channel();
		String addr = processor.getAddress(client);
		LOG.info("WebSocket Client:" + addr + "掉线");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Channel client = ctx.channel();
		String addr = processor.getAddress(client);
		LOG.info("WebSocket Client:" + addr + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

}
