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
package org.jboss.netty.channel.socket.oio;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.DefaultFileRegion;
import org.jboss.netty.channel.FileRegion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.SocketException;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;
import java.util.regex.Pattern;

import static org.jboss.netty.channel.Channels.*;

class OioWorker extends AbstractOioWorker<OioSocketChannel> {

    private static final Pattern SOCKET_CLOSED_MESSAGE = Pattern.compile(
            "^.*(?:Socket.*closed).*$", Pattern.CASE_INSENSITIVE);

    OioWorker(OioSocketChannel channel) {
        super(channel);
    }

    @Override
    public void run() {
        boolean fireConnected = channel instanceof OioAcceptedSocketChannel;
        if (fireConnected && channel.isOpen()) {
            // Fire the channelConnected event for OioAcceptedSocketChannel.
            // See #287
            fireChannelConnected(channel, channel.getRemoteAddress());
        }
        super.run();
    }

    @Override
    boolean process() throws IOException {
        byte[] buf;
        int readBytes;
        PushbackInputStream in = channel.getInputStream();
        int bytesToRead = in.available();
        if (bytesToRead > 0) {
            buf = new byte[bytesToRead];
            readBytes = in.read(buf);
        } else {
            int b = in.read();
            if (b < 0) {
                return false;
            }
            in.unread(b);
            return true;
        }
        fireMessageReceived(channel, channel.getConfig().getBufferFactory().getBuffer(buf, 0, readBytes));

        return true;
    }

    static void write(
            OioSocketChannel channel, ChannelFuture future,
            Object message) {

        boolean iothread = isIoThread(channel);
        OutputStream out = channel.getOutputStream();
        if (out == null) {
            Exception e = new ClosedChannelException();
            future.setFailure(e);
            if (iothread) {
                fireExceptionCaught(channel, e);
            } else {
                fireExceptionCaughtLater(channel, e);
            }
            return;
        }

        try {
            int length = 0;

            // Add support to write a FileRegion. This in fact will not give any performance gain
            // but at least it not fail and we did the best to emulate it
            if (message instanceof FileRegion) {
                FileRegion fr = (FileRegion) message;
                try {
                    synchronized (out) {
                        WritableByteChannel  bchannel = Channels.newChannel(out);

                        long i;
                        while ((i = fr.transferTo(bchannel, length)) > 0) {
                            length += i;
                            if (length >= fr.getCount()) {
                                break;
                            }
                        }
                    }
                } finally {
                    if (fr instanceof DefaultFileRegion) {
                        DefaultFileRegion dfr = (DefaultFileRegion) fr;
                        if (dfr.releaseAfterTransfer()) {
                            fr.releaseExternalResources();
                        }
                    }
                }
            } else {
                ChannelBuffer a = (ChannelBuffer) message;
                length = a.readableBytes();
                synchronized (out) {
                    a.getBytes(a.readerIndex(), out, length);
                }
            }

            future.setSuccess();
            if (iothread) {
                fireWriteComplete(channel, length);
            } else {
                fireWriteCompleteLater(channel, length);
            }

        } catch (Throwable t) {
            // Convert 'SocketException: Socket closed' to
            // ClosedChannelException.
            if (t instanceof SocketException &&
                    SOCKET_CLOSED_MESSAGE.matcher(
                            String.valueOf(t.getMessage())).matches()) {
                t = new ClosedChannelException();
            }
            future.setFailure(t);
            if (iothread) {
                fireExceptionCaught(channel, t);
            } else {
                fireExceptionCaughtLater(channel, t);
            }
        }
    }
}
