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
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders.Names;
import org.jboss.netty.handler.codec.http.HttpHeaders.Values;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Values.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.*;

/**
 * <p>
 * Performs server side opening and closing handshakes for web socket specification version <a
 * href="http://tools.ietf.org/html/draft-ietf-hybi-thewebsocketprotocol-00" >draft-ietf-hybi-thewebsocketprotocol-
 * 00</a>
 * </p>
 * <p>
 * A very large portion of this code was taken from the Netty 3.2 HTTP example.
 * </p>
 */
public class WebSocketServerHandshaker00 extends WebSocketServerHandshaker {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker00.class);

    /**
     * Constructor with default values
     *
     * @param webSocketURL
     *            URL for web socket communications. e.g "ws://myhost.com/mypath". Subsequent web socket frames will be
     *            sent to this URL.
     * @param subprotocols
     *            CSV of supported protocols
     */
    public WebSocketServerHandshaker00(String webSocketURL, String subprotocols) {
        this(webSocketURL, subprotocols, Long.MAX_VALUE);
    }

    /**
      * Constructor specifying the destination web socket location
      *
      * @param webSocketURL
      *            URL for web socket communications. e.g "ws://myhost.com/mypath".
      *            Subsequent web socket frames will be sent to this URL.
      * @param subprotocols
      *            CSV of supported protocols
      * @param maxFramePayloadLength
      *            Maximum allowable frame payload length. Setting this value to your application's requirement may
      *            reduce denial of service attacks using long data frames.
      */
     public WebSocketServerHandshaker00(String webSocketURL, String subprotocols, long maxFramePayloadLength) {
         super(WebSocketVersion.V00, webSocketURL, subprotocols, maxFramePayloadLength);
     }

    /**
     * <p>
     * Handle the web socket handshake for the web socket specification <a href=
     * "http://tools.ietf.org/html/draft-ietf-hybi-thewebsocketprotocol-00">HyBi version 0</a> and lower. This standard
     * is really a rehash of <a href="http://tools.ietf.org/html/draft-hixie-thewebsocketprotocol-76" >hixie-76</a> and
     * <a href="http://tools.ietf.org/html/draft-hixie-thewebsocketprotocol-75" >hixie-75</a>.
     * </p>
     *
     * <p>
     * Browser request to the server:
     * </p>
     *
     * <pre>
     * GET /demo HTTP/1.1
     * Upgrade: WebSocket
     * Connection: Upgrade
     * Host: example.com
     * Origin: http://example.com
     * Sec-WebSocket-Protocol: chat, sample
     * Sec-WebSocket-Key1: 4 @1  46546xW%0l 1 5
     * Sec-WebSocket-Key2: 12998 5 Y3 1  .P00
     *
     * ^n:ds[4U
     * </pre>
     *
     * <p>
     * Server response:
     * </p>
     *
     * <pre>
     * HTTP/1.1 101 WebSocket Protocol Handshake
     * Upgrade: WebSocket
     * Connection: Upgrade
     * Sec-WebSocket-Origin: http://example.com
     * Sec-WebSocket-Location: ws://example.com/demo
     * Sec-WebSocket-Protocol: sample
     *
     * 8jKS'y:G*Co,Wxa-
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
            logger.debug(String.format("Channel %s WS Version 00 server handshake", channel.getId()));
        }

        // Serve the WebSocket handshake request.
        if (!Values.UPGRADE.equalsIgnoreCase(req.headers().get(CONNECTION))
                || !WEBSOCKET.equalsIgnoreCase(req.headers().get(Names.UPGRADE))) {
            throw new WebSocketHandshakeException("not a WebSocket handshake request: missing upgrade");
        }

        // Hixie 75 does not contain these headers while Hixie 76 does
        boolean isHixie76 = req.headers().contains(SEC_WEBSOCKET_KEY1) && req.headers().contains(SEC_WEBSOCKET_KEY2);

        // Create the WebSocket handshake response.
        HttpResponse res = new DefaultHttpResponse(HTTP_1_1, new HttpResponseStatus(101,
                isHixie76 ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"));
        res.headers().add(Names.UPGRADE, WEBSOCKET);
        res.headers().add(CONNECTION, Values.UPGRADE);

        // Fill in the headers and contents depending on handshake method.
        if (isHixie76) {
            // New handshake method with a challenge:
            res.headers().add(SEC_WEBSOCKET_ORIGIN, req.headers().get(ORIGIN));
            res.headers().add(SEC_WEBSOCKET_LOCATION, getWebSocketUrl());
            String subprotocols = req.headers().get(SEC_WEBSOCKET_PROTOCOL);
            if (subprotocols != null) {
                String selectedSubprotocol = selectSubprotocol(subprotocols);
                if (selectedSubprotocol == null) {
                    throw new WebSocketHandshakeException("Requested subprotocol(s) not supported: " + subprotocols);
                } else {
                    res.headers().add(SEC_WEBSOCKET_PROTOCOL, selectedSubprotocol);
                    setSelectedSubprotocol(selectedSubprotocol);
                }
            }

            // Calculate the answer of the challenge.
            String key1 = req.headers().get(SEC_WEBSOCKET_KEY1);
            String key2 = req.headers().get(SEC_WEBSOCKET_KEY2);
            int a = (int) (Long.parseLong(key1.replaceAll("[^0-9]", "")) / key1.replaceAll("[^ ]", "").length());
            int b = (int) (Long.parseLong(key2.replaceAll("[^0-9]", "")) / key2.replaceAll("[^ ]", "").length());
            long c = req.getContent().readLong();
            ChannelBuffer input = ChannelBuffers.buffer(16);
            input.writeInt(a);
            input.writeInt(b);
            input.writeLong(c);
            res.setContent(WebSocketUtil.md5(input));
        } else {
            // Old Hixie 75 handshake method with no challenge:
            res.headers().add(WEBSOCKET_ORIGIN, req.headers().get(ORIGIN));
            res.headers().add(WEBSOCKET_LOCATION, getWebSocketUrl());
            String protocol = req.headers().get(WEBSOCKET_PROTOCOL);
            if (protocol != null) {
                res.headers().add(WEBSOCKET_PROTOCOL, selectSubprotocol(protocol));
            }
        }

        return writeHandshakeResponse(
                channel, res, new WebSocket00FrameEncoder(), new WebSocket00FrameDecoder(getMaxFramePayloadLength()));
    }

    /**
     * Echo back the closing frame
     *
     * @param channel
     *            Channel
     * @param frame
     *            Web Socket frame that was received
     */
    @Override
    public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
        return channel.write(frame);
    }
}
