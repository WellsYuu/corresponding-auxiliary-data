package com.tuling.netty.snake_game;


import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理TextWebSocketFrame
 */
public class SnakeGameHandler extends
        SimpleChannelInboundHandler<TextWebSocketFrame> {
    static final Logger logger = LoggerFactory.getLogger(SnakeGameEngine.class);

    private final ChannelGroup channels;
    private final SnakeGameEngine gameEngine;

    public SnakeGameHandler(SnakeGameEngine gameEngine, ChannelGroup channels) {
        this.channels = channels;
        this.gameEngine = gameEngine;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception { // (1)

        Channel incoming = ctx.channel();
        logger.debug("接收数据  地址:{},id:{},文本:{}", incoming.remoteAddress(), incoming.id().asShortText(), msg.text());

        String cmdText = msg.text().trim();
        int splitTindex;
        if ((splitTindex = cmdText.indexOf(":")) < 0) {
            logger.error("异常指令:{}", cmdText);
            return;
        }
        String cmd = cmdText.substring(0, splitTindex);
        String cmdData = cmdText.substring(splitTindex + 1);

        if (cmd.equals("JOIN")) {
            gameEngine.newSnake(incoming.id().asShortText(), cmdData);
        } else if (cmd.equals("CONTROL")) {
            gameEngine.controlSnake(incoming.id().asShortText(), Integer.parseInt(cmdData));
        } else if (cmd.equals("FULL")) { // 全量刷新
            String fullData = JSON.toJSONString(gameEngine.getCurrentMapData(false));
            fullData="version\r\n"+fullData;
            incoming.writeAndFlush(new TextWebSocketFrame(fullData));
        } else if (cmd.equals("QUANTITATIVE")) {// 定量更新
            String[] vTexts = cmdData.split(",");
            Long versions[] = new Long[vTexts.length];

            for (int i = 0; i < vTexts.length; i++) {
                versions[i] = Long.parseLong(vTexts[i]);
            }

            for (VersionData s : gameEngine.getVersion(versions)) {
                incoming.writeAndFlush(new TextWebSocketFrame("version\r\n"+JSON.toJSONString(s)));
            }
        } else if (cmd.equals("RESURGENCE")) { // 复活角色
            gameEngine.doResurgence(incoming.id().asShortText());
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        logger.info("[SERVER] - " + incoming.remoteAddress() + "加入");
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        logger.info("Client:" + incoming.remoteAddress() + "离开");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        logger.info("Client:" + incoming.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        logger.info("Client:" + incoming.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)    // (7)
            throws Exception {
        Channel incoming = ctx.channel();
        logger.error("Client:" + incoming.remoteAddress() + "异常", cause);
        ctx.close();
    }

}
