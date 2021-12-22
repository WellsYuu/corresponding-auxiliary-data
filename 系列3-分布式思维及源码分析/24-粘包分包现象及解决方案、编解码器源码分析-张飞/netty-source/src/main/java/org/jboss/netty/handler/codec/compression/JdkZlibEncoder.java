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
package org.jboss.netty.handler.codec.compression;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.LifeCycleAwareChannelHandler;
import org.jboss.netty.handler.codec.oneone.OneToOneStrictEncoder;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;
import java.util.zip.Deflater;


/**
 * Compresses a {@link ChannelBuffer} using the deflate algorithm.
 * @apiviz.landmark
 * @apiviz.has org.jboss.netty.handler.codec.compression.ZlibWrapper
 */
public class JdkZlibEncoder extends OneToOneStrictEncoder implements LifeCycleAwareChannelHandler {

    private final ZlibWrapper wrapper;
    private final Deflater deflater;
    private final AtomicBoolean finished = new AtomicBoolean();
    private volatile ChannelHandlerContext ctx;
    private byte[] out;

    private final CRC32 crc;
    private static final byte[] gzipHeader = {0x1f, (byte) 0x8b, Deflater.DEFLATED, 0, 0, 0, 0, 0, 0, 0};
    private boolean writeHeader = true;

    /**
     * Creates a new zlib encoder with the default compression level ({@code 6})
     * and the default wrapper ({@link ZlibWrapper#ZLIB}).
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public JdkZlibEncoder() {
        this(6);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel}
     * and the default wrapper ({@link ZlibWrapper#ZLIB}).
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public JdkZlibEncoder(int compressionLevel) {
        this(ZlibWrapper.ZLIB, compressionLevel);
    }

    /**
     * Creates a new zlib encoder with the default compression level ({@code 6})
     * and the specified wrapper.
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public JdkZlibEncoder(ZlibWrapper wrapper) {
        this(wrapper, 6);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel}
     * and the specified wrapper.
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public JdkZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException(
                    "compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException(
                    "wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " +
                    "allowed for compression.");
        }

        this.wrapper = wrapper;
        deflater = new Deflater(compressionLevel, wrapper != ZlibWrapper.ZLIB);
        if (wrapper == ZlibWrapper.GZIP) {
            crc = new CRC32();
        } else {
            crc = null;
        }
    }

    /**
     * Creates a new zlib encoder with the default compression level ({@code 6})
     * and the specified preset dictionary.  The wrapper is always
     * {@link ZlibWrapper#ZLIB} because it is the only format that supports
     * the preset dictionary.
     *
     * @param dictionary  the preset dictionary
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public JdkZlibEncoder(byte[] dictionary) {
        this(6, dictionary);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel}
     * and the specified preset dictionary.  The wrapper is always
     * {@link ZlibWrapper#ZLIB} because it is the only format that supports
     * the preset dictionary.
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     * @param dictionary  the preset dictionary
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public JdkZlibEncoder(int compressionLevel, byte[] dictionary) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException(
                    "compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }

        wrapper = ZlibWrapper.ZLIB;
        crc = null;
        deflater = new Deflater(compressionLevel);
        deflater.setDictionary(dictionary);
    }

    public ChannelFuture close() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            throw new IllegalStateException("not added to a pipeline");
        }
        return finishEncode(ctx, null);
    }

    private boolean isGzip() {
        return wrapper == ZlibWrapper.GZIP;
    }

    public boolean isClosed() {
        return finished.get();
    }

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof ChannelBuffer) || finished.get()) {
            return msg;
        }

        final ChannelBuffer uncompressed = (ChannelBuffer) msg;
        final int uncompressedLen = uncompressed.readableBytes();
        if (uncompressedLen == 0) {
            return uncompressed;
        }

        final byte[] in = new byte[uncompressedLen];
        uncompressed.readBytes(in);

        final int sizeEstimate = estimateCompressedSize(uncompressedLen);
        final ChannelBuffer compressed =
                ChannelBuffers.dynamicBuffer(sizeEstimate, channel.getConfig().getBufferFactory());

        synchronized (deflater) {
            if (isGzip()) {
                crc.update(in);
                if (writeHeader) {
                    compressed.writeBytes(gzipHeader);
                    writeHeader = false;
                }
            }

            deflater.setInput(in);
            while (!deflater.needsInput()) {
                deflate(compressed);
            }
        }

        return compressed;
    }

    private int estimateCompressedSize(int originalSize) {
        int sizeEstimate = (int) Math.ceil(originalSize * 1.001) + 12;
        if (writeHeader) {
            switch (wrapper) {
            case GZIP:
                sizeEstimate += gzipHeader.length;
                break;
            case ZLIB:
                sizeEstimate += 2; // first two magic bytes
                break;
            }
        }
        return sizeEstimate;
    }

    @Override
    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent evt)
            throws Exception {
        if (evt instanceof ChannelStateEvent) {
            ChannelStateEvent e = (ChannelStateEvent) evt;
            switch (e.getState()) {
            case OPEN:
            case CONNECTED:
            case BOUND:
                if (Boolean.FALSE.equals(e.getValue()) || e.getValue() == null) {
                    finishEncode(ctx, evt);
                    return;
                }
            }
        }

        super.handleDownstream(ctx, evt);
    }

    private ChannelFuture finishEncode(final ChannelHandlerContext ctx, final ChannelEvent evt) {
        ChannelFuture future = Channels.succeededFuture(ctx.getChannel());

        if (!finished.compareAndSet(false, true)) {
            if (evt != null) {
                ctx.sendDownstream(evt);
            }
            return future;
        }

        final ChannelBuffer footer = ChannelBuffers.dynamicBuffer(ctx.getChannel().getConfig().getBufferFactory());
        final boolean gzip = isGzip();
        synchronized (deflater) {
            if (gzip && writeHeader) {
                // Write the GZIP header first if not written yet. (i.e. user wrote nothing.)
                writeHeader = false;
                footer.writeBytes(gzipHeader);
            }

            deflater.finish();
            while (!deflater.finished()) {
                deflate(footer);
            }
            if (gzip) {
                int crcValue = (int) crc.getValue();
                int uncBytes = deflater.getTotalIn();
                footer.writeByte(crcValue);
                footer.writeByte(crcValue >>> 8);
                footer.writeByte(crcValue >>> 16);
                footer.writeByte(crcValue >>> 24);
                footer.writeByte(uncBytes);
                footer.writeByte(uncBytes >>> 8);
                footer.writeByte(uncBytes >>> 16);
                footer.writeByte(uncBytes >>> 24);
            }
            deflater.end();
        }

        if (footer.readable()) {
            future = Channels.future(ctx.getChannel());
            Channels.write(ctx, future, footer);
        }

        if (evt != null) {
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    ctx.sendDownstream(evt);
                }
            });
        }

        return future;
    }

    private void deflate(ChannelBuffer out) {
        int numBytes;
        if (out.hasArray()) {
            do {
                int writerIndex = out.writerIndex();
                numBytes = deflater.deflate(
                        out.array(), out.arrayOffset() + writerIndex, out.writableBytes(),
                        Deflater.SYNC_FLUSH);
                out.writerIndex(writerIndex + numBytes);
            } while (numBytes > 0);
        } else {
            byte[] tmpOut = this.out;
            if (tmpOut == null) {
                tmpOut = this.out = new byte[8192];
            }

            do {
                numBytes = deflater.deflate(tmpOut, 0, tmpOut.length, Deflater.SYNC_FLUSH);
                out.writeBytes(tmpOut, 0, numBytes);
            } while (numBytes > 0);
        }
    }

    public void beforeAdd(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    public void afterAdd(ChannelHandlerContext ctx) throws Exception {
        // Unused
    }

    public void beforeRemove(ChannelHandlerContext ctx) throws Exception {
        // Unused
    }

    public void afterRemove(ChannelHandlerContext ctx) throws Exception {
        // Unused
    }
}
