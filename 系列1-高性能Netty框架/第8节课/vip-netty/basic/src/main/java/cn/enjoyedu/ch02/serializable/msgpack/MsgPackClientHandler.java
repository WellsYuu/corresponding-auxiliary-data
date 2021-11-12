package cn.enjoyedu.ch02.serializable.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：Mark/Maoke
 * 创建日期：2018/08/26
 * 类说明：
 */
public class MsgPackClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final int sendNumber;

    public MsgPackClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    private AtomicInteger counter = new AtomicInteger(0);

    /*** 客户端读取到网络数据后的处理*/
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client Accept["+msg.toString(CharsetUtil.UTF_8)
                +"] and the counter is:"+counter.incrementAndGet());
    }

    /*** 客户端被通知channel活跃后，做事*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User[] users = makeUsers();
        //发送数据
        for(User user:users){
            System.out.println("Send user:"+user);
            ctx.write(user);
        }
        ctx.flush();
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /*生成用户实体类的数组，以供发送*/
    private User[] makeUsers(){
        User[] users=new User[sendNumber];
        User user =null;
        for(int i=0;i<sendNumber;i++){
            user=new User();
            user.setAge(i);
            String userName = "ABCDEFG --->"+i;
            user.setUserName(userName);
            user.setId("No:"+(sendNumber-i));
            user.setUserContact(
                    new UserContact(userName+"@xiangxue.com","133"));
            users[i]=user;
        }
        return users;
    }
}
