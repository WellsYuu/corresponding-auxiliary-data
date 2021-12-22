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

/**
 * Web Socket frame containing binary data
 */
public class BinaryWebSocketFrame extends WebSocketFrame {

    /**
     * Creates a new empty binary frame.
     */
    public BinaryWebSocketFrame() {
        setBinaryData(ChannelBuffers.EMPTY_BUFFER);
    }

    /**
     * Creates a new binary frame with the specified binary data. The final fragment flag is set to true.
     *
     * @param binaryData
     *            the content of the frame.
     */
    public BinaryWebSocketFrame(ChannelBuffer binaryData) {
        setBinaryData(binaryData);
    }

    /**
     * Creates a new binary frame with the specified binary data and the final fragment flag.
     *
     * @param finalFragment
     *            flag indicating if this frame is the final fragment
     * @param rsv
     *            reserved bits used for protocol extensions
     * @param binaryData
     *            the content of the frame.
     */
    public BinaryWebSocketFrame(boolean finalFragment, int rsv, ChannelBuffer binaryData) {
        setFinalFragment(finalFragment);
        setRsv(rsv);
        setBinaryData(binaryData);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(data: " + getBinaryData() + ')';
    }

}
