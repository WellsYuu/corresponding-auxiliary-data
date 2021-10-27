package cn.enjoyedu.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：增加handler
 */
public class WebSocketServerInitializer
        extends ChannelInitializer<SocketChannel> {

    private final ChannelGroup group;

    /*websocket访问路径*/
    private static final String WEBSOCKET_PATH = "/websocket";

    private final SslContext sslCtx;

    public WebSocketServerInitializer(SslContext sslCtx,ChannelGroup group) {
        this.sslCtx = sslCtx;
        this.group = group;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        /*增加对http的支持*/
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));

        /*Netty提供，支持WebSocket应答数据压缩传输*/
        pipeline.addLast(new WebSocketServerCompressionHandler());
        /*Netty提供，对整个websocket的通信进行了初始化
        (发现http报文中有升级为websocket的请求)
        ，包括握手，以及以后的一些通信控制*/
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH,
                null, true));

        /*浏览器访问时展示index页面*/
        pipeline.addLast(new ProcessWsIndexPageHandler(WEBSOCKET_PATH));

        /*对websocket的数据进行处理*/
        pipeline.addLast(new ProcesssWsFrameHandler(group));
    }
}
