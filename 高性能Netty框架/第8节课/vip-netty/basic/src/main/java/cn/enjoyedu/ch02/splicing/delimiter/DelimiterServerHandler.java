package cn.enjoyedu.ch02.splicing.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/25
 * 类说明：自己的业务处理
 */
@ChannelHandler.Sharable
public class DelimiterServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger counter = new AtomicInteger(0);

    /*** 服务端读取到网络数据后的处理*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        String request = in.toString(CharsetUtil.UTF_8);
        System.out.println("Server Accept["+request
                +"] and the counter is:"+counter.incrementAndGet());
        String resp = "Hello,"+request+". Welcome to Netty World!"
                + DelimiterEchoServer.DELIMITER_SYMBOL;
        ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
