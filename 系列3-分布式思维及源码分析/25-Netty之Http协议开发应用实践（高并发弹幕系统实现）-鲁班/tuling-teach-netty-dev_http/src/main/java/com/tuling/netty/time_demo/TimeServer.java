package com.tuling.netty.time_demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tommy on 2017/12/28.
 */
public class TimeServer {

    // 初始netty 服务
    // 启动器：ServerBootstrap
    //
    public void openSever(int port) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bootGroup = new NioEventLoopGroup(1); //connect \accept \read \write
        EventLoopGroup workGroup = new NioEventLoopGroup(3);
        bootstrap.group(bootGroup, workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new TimerServerHandler());
            }
        });
        try {
            System.out.println("服务启动成功");
            ChannelFuture f = bootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bootGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    private static class TimerServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//            ctx.fireChannelRead(msg);
            // 读取流
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String body = new String(bytes);
            System.out.println("netty 服务端接收消息:" + body);
            String result;
            if ("time".equals(body.trim())) {
                result = SimpleDateFormat.getDateTimeInstance().format(new Date());
            } else {
                result = "Bad Order!!";
            }
            // 写入流
            ByteBuf responseBuf = Unpooled.copiedBuffer(result.getBytes());
            ctx.write(responseBuf);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            // super.chann elReadComplete(ctx);
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }

    public static void main(String[] args) {
        TimeServer server = new TimeServer();
        server.openSever(8888);

    }
}
