/*
 * Copyright 2013 The Netty Project
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
package org.jboss.netty.handler.codec.spdy;

import org.jboss.netty.util.internal.StringUtil;

/**
 * The default {@link SpdyRstStreamFrame} implementation.
 */
public class DefaultSpdyRstStreamFrame extends DefaultSpdyStreamFrame
        implements SpdyRstStreamFrame {

    private SpdyStreamStatus status;

    /**
     * Creates a new instance.
     *
     * @param streamId   the Stream-ID of this frame
     * @param statusCode the Status code of this frame
     */
    public DefaultSpdyRstStreamFrame(int streamId, int statusCode) {
        this(streamId, SpdyStreamStatus.valueOf(statusCode));
    }

    /**
     * Creates a new instance.
     *
     * @param streamId the Stream-ID of this frame
     * @param status   the status of this frame
     */
    public DefaultSpdyRstStreamFrame(int streamId, SpdyStreamStatus status) {
        super(streamId);
        setStatus(status);
    }

    public SpdyStreamStatus getStatus() {
        return status;
    }

    public void setStatus(SpdyStreamStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName());
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Stream-ID = ");
        buf.append(getStreamId());
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Status: ");
        buf.append(getStatus().toString());
        return buf.toString();
    }
}
