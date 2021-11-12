package cn.enjoyedu.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：对http请求，将index的页面返回给前端
 */
public class ProcessWsIndexPageHandler
        extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String websocketPath;

    public ProcessWsIndexPageHandler(String websocketPath) {
        this.websocketPath = websocketPath;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                FullHttpRequest req) throws Exception {
        // 处理错误或者无法解析的http请求
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        //只允许Get请求
        if (req.method() != GET) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        // 发送index页面的内容
        if ("/".equals(req.uri()) || "/index.html".equals(req.uri())) {
            //生成WebSocket的访问地址，写入index页面中
            String webSocketLocation
                    = getWebSocketLocation(ctx.pipeline(), req,
                    websocketPath);
            System.out.println("WebSocketLocation:["+webSocketLocation+"]");
            //生成index页面的具体内容,并送往浏览器
            ByteBuf content
                    = MakeIndexPage.getContent(
                            webSocketLocation);
            FullHttpResponse res = new DefaultFullHttpResponse(
                    HTTP_1_1, OK, content);

            res.headers().set(HttpHeaderNames.CONTENT_TYPE,
                    "text/html; charset=UTF-8");
            HttpUtil.setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);
        } else {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /*发送应答*/
    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req,
                                         FullHttpResponse res) {
        // 错误的请求进行处理 （code<>200).
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // 发送应答.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        //对于不是长连接或者错误的请求直接关闭连接
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /*根据用户的访问，告诉用户的浏览器，WebSocket的访问地址*/
    private static String getWebSocketLocation(ChannelPipeline cp,
                                               HttpRequest req,
                                               String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            protocol = "wss";
        }
        return protocol + "://" + req.headers().get(HttpHeaderNames.HOST)
                + path;
    }
}
