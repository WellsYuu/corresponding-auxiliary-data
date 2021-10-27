package cn.enjoyedu.ch02.serializable.msgpack;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/25
 * 类说明：自己的业务处理
 */
@ChannelHandler.Sharable
public class MsgPackServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger counter = new AtomicInteger(0);

    /*** 服务端读取到网络数据后的处理*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        List<User> users = (List<User>)msg;
        System.out.println("Server Accept["+users
                +"] and the counter is:"+counter.incrementAndGet());
        String resp = "I process user :"+users.get(0)
                + System.getProperty("line.separator");
        ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
