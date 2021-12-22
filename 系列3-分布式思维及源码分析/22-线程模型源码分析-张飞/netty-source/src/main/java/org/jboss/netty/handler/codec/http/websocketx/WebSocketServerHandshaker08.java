/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.handler.codec.http.websocketx;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders.Names;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.CharsetUtil;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Values.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.*;

/**
 * <p>
 * Performs server side opening and closing handshakes for web socket specification version <a
 * href="http://tools.ietf.org/html/draft-ietf-hybi-thewebsocketprotocol-10" >draft-ietf-hybi-thewebsocketprotocol-
 * 10</a>
 * </p>
 */
public class WebSocketServerHandshaker08 extends WebSocketServerHandshaker {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker08.class);

    public static final String WEBSOCKET_08_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    private final boolean allowExtensions;

    /**
     * Constructor using defaults
     *
     * @param webSocketURL
     *            URL for web socket communications. e.g "ws://myhost.com/mypath". Subsequent web socket frames will be
     *            sent to this URL.
     * @param subprotocols
     *            CSV of supported protocols
     * @param allowExtensions
     *            Allow extensions to be used in the reserved bits of the web socket frame
     */
    public WebSocketServerHandshaker08(String webSocketURL, String subprotocols, boolean allowExtensions) {
        this(webSocketURL, subprotocols, allowExtensions, Long.MAX_VALUE);
    }

    /**
     * Constructor
     *
     * @param webSocketURL
     *            URL for web socket communications. e.g "ws://myhost.com/mypath". Subsequent web socket frames will be
     *            sent to this URL.
     * @param subprotocols
     *            CSV of supported protocols
     * @param allowExtensions
     *            Allow extensions to be used in the reserved bits of the web socket frame
     * @param maxFramePayloadLength
     *            Maximum allowable frame payload length. Setting this value to your application's requirement may
     *            reduce denial of service attacks using long data frames.
     */
    public WebSocketServerHandshaker08(String webSocketURL, String subprotocols, boolean allowExtensions,
            long maxFramePayloadLength) {
        super(WebSocketVersion.V08, webSocketURL, subprotocols, maxFramePayloadLength);
        this.allowExtensions = allowExtensions;
    }

    /**
     * <p>
     * Handle the web socket handshake for the web socket specification <a href=
     * "http://tools.ietf.org/html/draft-ietf-hybi-thewebsocketprotocol-08">HyBi version 8 to 10</a>. Version 8, 9 and
     * 10 share the same wire protocol.
     * </p>
     *
     * <p>
     * Browser request to the server:
     * </p>
     *
     * <pre>
     * GET /chat HTTP/1.1
     * Host: server.example.com
     * Upgrade: websocket
     * Connection: Upgrade
     * Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
     * Sec-WebSocket-Origin: http://example.com
     * Sec-WebSocket-Protocol: chat, superchat
     * Sec-WebSocket-Version: 8
     * </pre>
     *
     * <p>
     * Server response:
     * </p>
     *
     * <pre>
     * HTTP/1.1 101 Switching Protocols
     * Upgrade: websocket
     * Connection: Upgrade
     * Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
     * Sec-WebSocket-Protocol: chat
     * </pre>
     *
     * @param channel
     *            Channel
     * @param req
     *            HTTP request
     */
    @Override
    public ChannelFuture handshake(Channel channel, HttpRequest req) {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Channel %s WS Version 8 server handshake", channel.getId()));
        }

        HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);

        String key = req.headers().get(Names.SEC_WEBSOCKET_KEY);
        if (key == null) {
            throw new WebSocketHandshakeException("not a WebSocket request: missing key");
        }
        String acceptSeed = key + WEBSOCKET_08_ACCEPT_GUID;
        ChannelBuffer sha1 = WebSocketUtil.sha1(ChannelBuffers.copiedBuffer(acceptSeed, CharsetUtil.US_ASCII));

        String accept = WebSocketUtil.base64(sha1);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("WS Version 8 Server Handshake key: %s. Response: %s.", key, accept));
        }

        res.setStatus(HttpResponseStatus.SWITCHING_PROTOCOLS);
        res.headers().add(Names.UPGRADE, WEBSOCKET.toLowerCase());
        res.headers().add(Names.CONNECTION, Names.UPGRADE);
        res.headers().add(Names.SEC_WEBSOCKET_ACCEPT, accept);
        String subprotocols = req.headers().get(Names.SEC_WEBSOCKET_PROTOCOL);
        if (subprotocols != null) {
            String selectedSubprotocol = selectSubprotocol(subprotocols);
            if (selectedSubprotocol == null) {
                throw new WebSocketHandshakeException("Requested subprotocol(s) not supported: " + subprotocols);
            } else {
                res.headers().add(Names.SEC_WEBSOCKET_PROTOCOL, selectedSubprotocol);
                setSelectedSubprotocol(selectedSubprotocol);
            }
        }

        return writeHandshakeResponse(
                channel, res, new WebSocket08FrameEncoder(false),
                              new WebSocket08FrameDecoder(true, allowExtensions, getMaxFramePayloadLength()));
    }

    /**
     * Echo back the closing frame and close the connection
     *
     * @param channel
     *            Channel
     * @param frame
     *            Web Socket frame that was received
     */
    @Override
    public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
        ChannelFuture f = channel.write(frame);
        f.addListener(ChannelFutureListener.CLOSE);
        return f;
    }

}
