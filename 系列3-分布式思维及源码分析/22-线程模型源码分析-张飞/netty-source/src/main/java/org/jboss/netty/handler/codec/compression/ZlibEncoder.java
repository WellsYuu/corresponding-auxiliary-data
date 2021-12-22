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
import org.jboss.netty.util.internal.jzlib.JZlib;
import org.jboss.netty.util.internal.jzlib.ZStream;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Compresses a {@link ChannelBuffer} using the deflate algorithm.
 * @apiviz.landmark
 * @apiviz.has org.jboss.netty.handler.codec.compression.ZlibWrapper
 */
public class ZlibEncoder extends OneToOneStrictEncoder implements LifeCycleAwareChannelHandler {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    private final int wrapperOverhead;
    private final ZStream z = new ZStream();
    private final AtomicBoolean finished = new AtomicBoolean();
    private volatile ChannelHandlerContext ctx;

    /**
     * Creates a new zlib encoder with the default compression level ({@code 6}),
     * default window bits ({@code 15}), default memory level ({@code 8}),
     * and the default wrapper ({@link ZlibWrapper#ZLIB}).
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public ZlibEncoder() {
        this(6);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel},
     * default window bits ({@code 15}), default memory level ({@code 8}),
     * and the default wrapper ({@link ZlibWrapper#ZLIB}).
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public ZlibEncoder(int compressionLevel) {
        this(ZlibWrapper.ZLIB, compressionLevel);
    }

    /**
     * Creates a new zlib encoder with the default compression level ({@code 6}),
     * default window bits ({@code 15}), default memory level ({@code 8}),
     * and the specified wrapper.
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public ZlibEncoder(ZlibWrapper wrapper) {
        this(wrapper, 6);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel},
     * default window bits ({@code 15}), default memory level ({@code 8}),
     * and the specified wrapper.
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public ZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
        this(wrapper, compressionLevel, 15, 8);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel},
     * the specified {@code windowBits}, the specified {@code memLevel}, and
     * the specified wrapper.
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     * @param windowBits
     *        The base two logarithm of the size of the history buffer.  The
     *        value should be in the range {@code 9} to {@code 15} inclusive.
     *        Larger values result in better compression at the expense of
     *        memory usage.  The default value is {@code 15}.
     * @param memLevel
     *        How much memory should be allocated for the internal compression
     *        state.  {@code 1} uses minimum memory and {@code 9} uses maximum
     *        memory.  Larger values result in better and faster compression
     *        at the expense of memory usage.  The default value is {@code 8}
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public ZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException(
                    "compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException(
                    "windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException(
                    "memLevel: " + memLevel + " (expected: 1-9)");
        }
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException(
                    "wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " +
                    "allowed for compression.");
        }

        wrapperOverhead = ZlibUtil.wrapperOverhead(wrapper);

        synchronized (z) {
            int resultCode = z.deflateInit(compressionLevel, windowBits, memLevel,
                    ZlibUtil.convertWrapperType(wrapper));
            if (resultCode != JZlib.Z_OK) {
                ZlibUtil.fail(z, "initialization failure", resultCode);
            }
        }
    }

    /**
     * Creates a new zlib encoder with the default compression level ({@code 6}),
     * default window bits ({@code 15}), default memory level ({@code 8}),
     * and the specified preset dictionary.  The wrapper is always
     * {@link ZlibWrapper#ZLIB} because it is the only format that supports
     * the preset dictionary.
     *
     * @param dictionary  the preset dictionary
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public ZlibEncoder(byte[] dictionary) {
        this(6, dictionary);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel},
     * default window bits ({@code 15}), default memory level ({@code 8}),
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
    public ZlibEncoder(int compressionLevel, byte[] dictionary) {
        this(compressionLevel, 15, 8, dictionary);
    }

    /**
     * Creates a new zlib encoder with the specified {@code compressionLevel},
     * the specified {@code windowBits}, the specified {@code memLevel},
     * and the specified preset dictionary.  The wrapper is always
     * {@link ZlibWrapper#ZLIB} because it is the only format that supports
     * the preset dictionary.
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     * @param windowBits
     *        The base two logarithm of the size of the history buffer.  The
     *        value should be in the range {@code 9} to {@code 15} inclusive.
     *        Larger values result in better compression at the expense of
     *        memory usage.  The default value is {@code 15}.
     * @param memLevel
     *        How much memory should be allocated for the internal compression
     *        state.  {@code 1} uses minimum memory and {@code 9} uses maximum
     *        memory.  Larger values result in better and faster compression
     *        at the expense of memory usage.  The default value is {@code 8}
     * @param dictionary  the preset dictionary
     *
     * @throws CompressionException if failed to initialize zlib
     */
    public ZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException(
                    "compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException(
                    "windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException(
                    "memLevel: " + memLevel + " (expected: 1-9)");
        }
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }

        wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);

        synchronized (z) {
            int resultCode;
            resultCode = z.deflateInit(compressionLevel, windowBits, memLevel,
                    JZlib.W_ZLIB); // Default: ZLIB format
            if (resultCode != JZlib.Z_OK) {
                ZlibUtil.fail(z, "initialization failure", resultCode);
            } else {
                resultCode = z.deflateSetDictionary(dictionary, dictionary.length);
                if (resultCode != JZlib.Z_OK) {
                    ZlibUtil.fail(z, "failed to set the dictionary", resultCode);
                }
            }
        }
    }

    public ChannelFuture close() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            throw new IllegalStateException("not added to a pipeline");
        }
        return finishEncode(ctx, null);
    }

    public boolean isClosed() {
        return finished.get();
    }

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof ChannelBuffer) || finished.get()) {
            return msg;
        }

        final ChannelBuffer result;
        synchronized (z) {
            try {
                // Configure input.
                final ChannelBuffer uncompressed = (ChannelBuffer) msg;
                final int uncompressedLen = uncompressed.readableBytes();
                if (uncompressedLen == 0) {
                    return uncompressed;
                }

                final byte[] in = new byte[uncompressedLen];
                uncompressed.readBytes(in);
                z.next_in = in;
                z.next_in_index = 0;
                z.avail_in = uncompressedLen;

                // Configure output.
                final byte[] out = new byte[(int) Math.ceil(uncompressedLen * 1.001) + 12 + wrapperOverhead];
                z.next_out = out;
                z.next_out_index = 0;
                z.avail_out = out.length;

                // Note that Z_PARTIAL_FLUSH has been deprecated.
                final int resultCode = z.deflate(JZlib.Z_SYNC_FLUSH);
                if (resultCode != JZlib.Z_OK) {
                    ZlibUtil.fail(z, "compression failure", resultCode);
                }

                if (z.next_out_index != 0) {
                    result = ctx.getChannel().getConfig().getBufferFactory().getBuffer(
                            uncompressed.order(), out, 0, z.next_out_index);
                } else {
                    result = ChannelBuffers.EMPTY_BUFFER;
                }
            } finally {
                // Deference the external references explicitly to tell the VM that
                // the allocated byte arrays are temporary so that the call stack
                // can be utilized.
                // I'm not sure if the modern VMs do this optimization though.
                z.next_in = null;
                z.next_out = null;
            }
        }

        return result;
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
        if (!finished.compareAndSet(false, true)) {
            if (evt != null) {
                ctx.sendDownstream(evt);
            }
            return Channels.succeededFuture(ctx.getChannel());
        }

        ChannelBuffer footer;
        ChannelFuture future;
        synchronized (z) {
            try {
                // Configure input.
                z.next_in = EMPTY_ARRAY;
                z.next_in_index = 0;
                z.avail_in = 0;

                // Configure output.
                byte[] out = new byte[32]; // room for ADLER32 + ZLIB / CRC32 + GZIP header
                z.next_out = out;
                z.next_out_index = 0;
                z.avail_out = out.length;

                // Write the ADLER32 checksum (stream footer).
                int resultCode = z.deflate(JZlib.Z_FINISH);
                if (resultCode != JZlib.Z_OK && resultCode != JZlib.Z_STREAM_END) {
                    future = Channels.failedFuture(
                            ctx.getChannel(),
                            ZlibUtil.exception(z, "compression failure", resultCode));
                    footer = null;
                } else if (z.next_out_index != 0) {
                    future = Channels.future(ctx.getChannel());
                    footer =
                        ctx.getChannel().getConfig().getBufferFactory().getBuffer(
                                out, 0, z.next_out_index);
                } else {
                    // Note that we should never use a SucceededChannelFuture
                    // here just in case any downstream handler or a sink wants
                    // to notify a write error.
                    future = Channels.future(ctx.getChannel());
                    footer = ChannelBuffers.EMPTY_BUFFER;
                }
            } finally {
                z.deflateEnd();

                // Deference the external references explicitly to tell the VM that
                // the allocated byte arrays are temporary so that the call stack
                // can be utilized.
                // I'm not sure if the modern VMs do this optimization though.
                z.next_in = null;
                z.next_out = null;
            }
        }

        if (footer != null) {
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
