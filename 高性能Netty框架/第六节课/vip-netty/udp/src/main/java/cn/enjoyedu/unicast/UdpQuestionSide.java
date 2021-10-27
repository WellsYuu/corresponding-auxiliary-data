package cn.enjoyedu.unicast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class UdpQuestionSide {

    public final static String QUESTION = "告诉我一句古诗";

    public void run(int port) throws Exception{

        EventLoopGroup group  = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    /*由于我们用的是UDP协议，所以要用NioDatagramChannel来创建*/
                    //TODO
                    .handler(new QuestoinHandler());
            Channel ch = b.bind(0).sync().channel();
            //TODO 发出问题
            if(!ch.closeFuture().await(15000)){
                System.out.println("查询超时！");
            }
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
    public static void main(String [] args) throws Exception{
        int answerPort = 8080;

        new UdpQuestionSide().run(answerPort);
    }
}
