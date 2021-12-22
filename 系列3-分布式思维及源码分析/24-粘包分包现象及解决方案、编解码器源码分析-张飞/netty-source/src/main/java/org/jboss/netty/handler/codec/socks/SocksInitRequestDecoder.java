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
package org.jboss.netty.handler.codec.socks;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Decodes {@link ChannelBuffer}s into {@link SocksInitRequest}.
 * Before returning SocksRequest decoder removes itself from pipeline.
 */
public class SocksInitRequestDecoder extends ReplayingDecoder<SocksInitRequestDecoder.State> {

    private final List<SocksMessage.AuthScheme> authSchemes = new ArrayList<SocksMessage.AuthScheme>();
    private SocksMessage.ProtocolVersion version;
    private byte authSchemeNum;
    private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;

    public SocksInitRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel,
                            ChannelBuffer buffer, State state) throws Exception {
        switch (state) {
            case CHECK_PROTOCOL_VERSION: {
                version = SocksMessage.ProtocolVersion.fromByte(buffer.readByte());
                if (version != SocksMessage.ProtocolVersion.SOCKS5) {
                    break;
                }
                checkpoint(State.READ_AUTH_SCHEMES);
            }
            case READ_AUTH_SCHEMES: {
                authSchemes.clear();
                authSchemeNum = buffer.readByte();
                for (int i = 0; i < authSchemeNum; i++) {
                    authSchemes.add(SocksMessage.AuthScheme.fromByte(buffer.readByte()));
                }
                msg = new SocksInitRequest(authSchemes);
                break;
            }
        }
        ctx.getPipeline().remove(this);
        return msg;
    }

    enum State {
        CHECK_PROTOCOL_VERSION,
        READ_AUTH_SCHEMES
    }
}
