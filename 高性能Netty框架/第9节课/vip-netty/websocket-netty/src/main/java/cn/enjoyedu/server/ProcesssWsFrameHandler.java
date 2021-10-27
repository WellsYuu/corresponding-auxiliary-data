package cn.enjoyedu.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：对websocket的数据进行处理
 */
public class ProcesssWsFrameHandler
        extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final ChannelGroup group;

    public ProcesssWsFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    private static final Logger logger
            = LoggerFactory.getLogger(ProcesssWsFrameHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                WebSocketFrame frame) throws Exception {
        //判断是否为文本帧，目前只处理文本帧
        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            logger.info("{} received {}", ctx.channel(), request);
            ctx.channel().writeAndFlush(
                    new TextWebSocketFrame(request.toUpperCase(Locale.CHINA)));
            /*群发实现，Mark增加，一对一道理一样*/
            group.writeAndFlush(new TextWebSocketFrame(
                    "Client " + ctx.channel() + " say:"+request.toUpperCase(Locale.CHINA)));
        } else {
            String message = "unsupported frame type: "
                    + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    /*重写 userEventTriggered()方法以处理自定义事件*/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx,
                                   Object evt) throws Exception {
        /*检测事件，如果是握手成功事件，做点业务处理*/
        if (evt == WebSocketServerProtocolHandler
                .ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {

            //通知所有已经连接的 WebSocket 客户端新的客户端已经连接上了
            group.writeAndFlush(new TextWebSocketFrame(
                    "Client " + ctx.channel() + " joined"));

            //将新的 WebSocket Channel 添加到 ChannelGroup 中，
            // 以便它可以接收到所有的消息
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
