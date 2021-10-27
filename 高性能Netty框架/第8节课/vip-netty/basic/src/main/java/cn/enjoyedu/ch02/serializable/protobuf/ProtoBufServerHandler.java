package cn.enjoyedu.ch02.serializable.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class ProtoBufServerHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //将上一个handler传入的数据强制转型
        PersonProto.Person req = (PersonProto.Person) msg;
        System.out.println("收到数据：name="+req.getName()+",email="+req.getEmail());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if(cause instanceof IOException){
            System.out.println("远程客户端强迫关闭了一个现有的连接。");
        }
        ctx.close();
    }
}
