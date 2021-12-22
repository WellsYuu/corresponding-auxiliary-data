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
package org.jboss.netty.handler.codec.http;

import static org.jboss.netty.handler.codec.http.HttpConstants.*;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Encodes an {@link HttpResponse} or an {@link HttpChunk} into
 * a {@link ChannelBuffer}.
 */
public class HttpResponseEncoder extends HttpMessageEncoder {

    @Override
    protected void encodeInitialLine(ChannelBuffer buf, HttpMessage message) throws Exception {
        HttpResponse response = (HttpResponse) message;
        encodeAscii(response.getProtocolVersion().toString(), buf);
        buf.writeByte(SP);
        encodeAscii(String.valueOf(response.getStatus().getCode()), buf);
        buf.writeByte(SP);
        encodeAscii(String.valueOf(response.getStatus().getReasonPhrase()), buf);
        buf.writeByte(CR);
        buf.writeByte(LF);
    }
}
