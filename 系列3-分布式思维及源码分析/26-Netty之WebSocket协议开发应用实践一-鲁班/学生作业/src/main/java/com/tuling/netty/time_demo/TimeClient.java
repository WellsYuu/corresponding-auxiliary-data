package com.tuling.netty.time_demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.IOException;

/**
 * Created by Tommy on 2017/12/28.
 */
public class TimeClient {

    private final TimerClientHandler client;

    public TimeClient(String host, int port) {
        final Bootstrap bootstrap = new Bootstrap();
        final EventLoopGroup group = new NioEventLoopGroup(1);
        client = new TimerClientHandler();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.remoteAddress(host, port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(client);
            }
        });


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ChannelFuture f = bootstrap.connect().sync();
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("连接关闭，资源释放 ");
                    group.shutdownGracefully();
                }
            }
        }, "IO Client");
        t.setDaemon(true);
        t.start();

    }

    public void sendMessage(String msg) {
        ByteBuf msgBuf = Unpooled.buffer(msg.getBytes().length);
        msgBuf.writeBytes(msg.getBytes());
        client.ctx.writeAndFlush(msgBuf);
    }

    public void close() {
        client.ctx.close();
    }

    private static class TimerClientHandler extends ChannelInboundHandlerAdapter {
        private ChannelHandlerContext ctx;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            String body = "time";
            ByteBuf msg = Unpooled.buffer(body.getBytes().length);
            msg.writeBytes(body.getBytes());
            ctx.writeAndFlush(msg);
            this.ctx = ctx;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String body = new String(bytes);
            System.out.println("当前服务时间：" + body);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws IOException {
        TimeClient client = new TimeClient("127.0.0.1", 8888);
        while (true) {
            byte[] bytes = new byte[1024];
            int size = System.in.read(bytes);
            String cmd = new String(bytes, 0, size).trim();
            try {
                if (cmd.equals("close")) {
                    client.close();
                } else {
                    client.sendMessage(cmd);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
