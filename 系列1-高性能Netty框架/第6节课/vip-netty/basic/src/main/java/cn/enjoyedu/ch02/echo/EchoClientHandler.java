package cn.enjoyedu.ch02.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /*客户端读取到数据后干什么*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
            throws Exception {
        System.out.println("Client accetp:"+msg.toString(CharsetUtil.UTF_8));
    }

    /*客户端被通知channel活跃以后，做事*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //往服务器写数据
        //ctx.writeAndFlush("String");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Netty",
                CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
