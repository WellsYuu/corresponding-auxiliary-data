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
package org.jboss.netty.handler.stream;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.LifeCycleAwareChannelHandler;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.jboss.netty.channel.Channels.*;

/**
 * A {@link ChannelHandler} that adds support for writing a large data stream
 * asynchronously neither spending a lot of memory nor getting
 * {@link OutOfMemoryError}.  Large data streaming such as file
 * transfer requires complicated state management in a {@link ChannelHandler}
 * implementation.  {@link ChunkedWriteHandler} manages such complicated states
 * so that you can send a large data stream without difficulties.
 * <p>
 * To use {@link ChunkedWriteHandler} in your application, you have to insert
 * a new {@link ChunkedWriteHandler} instance:
 * <pre>
 * {@link ChannelPipeline} p = ...;
 * p.addLast("streamer", <b>new {@link ChunkedWriteHandler}()</b>);
 * p.addLast("handler", new MyHandler());
 * </pre>
 * Once inserted, you can write a {@link ChunkedInput} so that the
 * {@link ChunkedWriteHandler} can pick it up and fetch the content of the
 * stream chunk by chunk and write the fetched chunk downstream:
 * <pre>
 * {@link Channel} ch = ...;
 * ch.write(new {@link ChunkedFile}(new File("video.mkv"));
 * </pre>
 *
 * <h3>Sending a stream which generates a chunk intermittently</h3>
 *
 * Some {@link ChunkedInput} generates a chunk on a certain event or timing.
 * Such {@link ChunkedInput} implementation often returns {@code null} on
 * {@link ChunkedInput#nextChunk()}, resulting in the indefinitely suspended
 * transfer.  To resume the transfer when a new chunk is available, you have to
 * call {@link #resumeTransfer()}.
 * @apiviz.landmark
 * @apiviz.has org.jboss.netty.handler.stream.ChunkedInput oneway - - reads from
 */
public class ChunkedWriteHandler
        implements ChannelUpstreamHandler, ChannelDownstreamHandler, LifeCycleAwareChannelHandler {

    private static final InternalLogger logger =
        InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);

    private final Queue<MessageEvent> queue = new ConcurrentLinkedQueue<MessageEvent>();

    private volatile ChannelHandlerContext ctx;
    private final AtomicBoolean flush = new AtomicBoolean(false);
    private MessageEvent currentEvent;
    private volatile boolean flushNeeded;

    /**
     * Continues to fetch the chunks from the input.
     */
    public void resumeTransfer() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            return;
        }

        try {
            flush(ctx, false);
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("Unexpected exception while sending chunks.", e);
            }
        }
    }

    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e)
            throws Exception {
        if (!(e instanceof MessageEvent)) {
            ctx.sendDownstream(e);
            return;
        }

        boolean offered = queue.offer((MessageEvent) e);
        assert offered;

        final Channel channel = ctx.getChannel();
        // call flush if the channel is writable or not connected. flush(..) will take care of the rest

        if (channel.isWritable() || !channel.isConnected()) {
            this.ctx = ctx;
            flush(ctx, false);
        }
    }

    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
            throws Exception {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent cse = (ChannelStateEvent) e;
            switch (cse.getState()) {
            case INTEREST_OPS:
                // Continue writing when the channel becomes writable.
                flush(ctx, true);
                break;
            case OPEN:
                if (!Boolean.TRUE.equals(cse.getValue())) {
                    // Fail all pending writes
                    flush(ctx, true);
                }
                break;
            }
        }
        ctx.sendUpstream(e);
    }

    private void discard(ChannelHandlerContext ctx, boolean fireNow) {
        ClosedChannelException cause = null;

        for (;;) {
            MessageEvent currentEvent = this.currentEvent;

            if (this.currentEvent == null) {
                currentEvent = queue.poll();
            } else {
                this.currentEvent = null;
            }

            if (currentEvent == null) {
                break;
            }

            Object m = currentEvent.getMessage();
            if (m instanceof ChunkedInput) {
                closeInput((ChunkedInput) m);
            }

            // Trigger a ClosedChannelException
            if (cause == null) {
                cause = new ClosedChannelException();
            }
            currentEvent.getFuture().setFailure(cause);
        }

        if (cause != null) {
            if (fireNow) {
                fireExceptionCaught(ctx.getChannel(), cause);
            } else {
                fireExceptionCaughtLater(ctx.getChannel(), cause);
            }
        }
    }

    private void flush(ChannelHandlerContext ctx, boolean fireNow) throws Exception {
        boolean acquired;
        final Channel channel = ctx.getChannel();
        boolean suspend = false;
        flushNeeded = true;
        // use CAS to see if the have flush already running, if so we don't need to take futher actions
        if (acquired = flush.compareAndSet(false, true)) {
            flushNeeded = false;
            try {
                if (!channel.isConnected()) {
                    discard(ctx, fireNow);
                    return;
                }

                while (channel.isWritable()) {
                    if (currentEvent == null) {
                        currentEvent = queue.poll();
                    }

                    if (currentEvent == null) {
                        break;
                    }

                    if (currentEvent.getFuture().isDone()) {
                        // Skip the current request because the previous partial write
                        // attempt for the current request has been failed.
                        currentEvent = null;
                    } else {
                        final MessageEvent currentEvent = this.currentEvent;
                        Object m = currentEvent.getMessage();
                        if (m instanceof ChunkedInput) {
                            final ChunkedInput chunks = (ChunkedInput) m;
                            Object chunk;
                            boolean endOfInput;
                            try {
                                chunk = chunks.nextChunk();
                                endOfInput = chunks.isEndOfInput();
                                if (chunk == null) {
                                    chunk = ChannelBuffers.EMPTY_BUFFER;
                                    // No need to suspend when reached at the end.
                                    suspend = !endOfInput;
                                } else {
                                    suspend = false;
                                }
                            } catch (Throwable t) {
                                this.currentEvent = null;

                                currentEvent.getFuture().setFailure(t);
                                if (fireNow) {
                                    fireExceptionCaught(ctx, t);
                                } else {
                                    fireExceptionCaughtLater(ctx, t);
                                }

                                closeInput(chunks);
                                break;
                            }

                            if (suspend) {
                                // ChunkedInput.nextChunk() returned null and it has
                                // not reached at the end of input.  Let's wait until
                                // more chunks arrive.  Nothing to write or notify.
                                break;
                            } else {
                                ChannelFuture writeFuture;
                                if (endOfInput) {
                                    this.currentEvent = null;
                                    writeFuture = currentEvent.getFuture();

                                    // Register a listener which will close the input once the write
                                    // is complete. This is needed because the Chunk may have some
                                    // resource bound that can not be closed before its not written
                                    //
                                    // See https://github.com/netty/netty/issues/303
                                    writeFuture.addListener(new ChannelFutureListener() {

                                        public void operationComplete(ChannelFuture future) throws Exception {
                                            closeInput(chunks);
                                        }
                                    });
                                } else {
                                    writeFuture = future(channel);
                                    writeFuture.addListener(new ChannelFutureListener() {
                                        public void operationComplete(ChannelFuture future) throws Exception {
                                            if (!future.isSuccess()) {
                                                currentEvent.getFuture().setFailure(future.getCause());
                                                closeInput((ChunkedInput) currentEvent.getMessage());
                                            }
                                        }
                                    });
                                }

                                write(
                                        ctx, writeFuture, chunk,
                                        currentEvent.getRemoteAddress());
                            }
                        } else {
                            this.currentEvent = null;
                            ctx.sendDownstream(currentEvent);
                        }
                    }

                    if (!channel.isConnected()) {
                        discard(ctx, fireNow);
                        return;
                    }
                }
            } finally {
                // mark the flush as done
                flush.set(false);
            }
        }

        if (acquired && (!channel.isConnected() || channel.isWritable() && !queue.isEmpty() && !suspend
                || flushNeeded)) {
            flush(ctx, fireNow);
        }
    }

    static void closeInput(ChunkedInput chunks) {
        try {
            chunks.close();
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to close a chunked input.", t);
            }
        }
    }

    public void beforeAdd(ChannelHandlerContext ctx) throws Exception {
        // nothing to do
    }

    public void afterAdd(ChannelHandlerContext ctx) throws Exception {
        // nothing to do
    }

    public void beforeRemove(ChannelHandlerContext ctx) throws Exception {
        // try to flush again a last time.
        //
        // See #304
        flush(ctx, false);
    }

    // This method should not need any synchronization as the ChunkedWriteHandler will not receive any new events
    public void afterRemove(ChannelHandlerContext ctx) throws Exception {
        // Fail all MessageEvent's that are left. This is needed because otherwise we would never notify the
        // ChannelFuture and the registered FutureListener. See #304
        Throwable cause = null;
        boolean fireExceptionCaught = false;

        for (;;) {
            MessageEvent currentEvent = this.currentEvent;

            if (this.currentEvent == null) {
                currentEvent = queue.poll();
            } else {
                this.currentEvent = null;
            }

            if (currentEvent == null) {
                break;
            }

            Object m = currentEvent.getMessage();
            if (m instanceof ChunkedInput) {
                closeInput((ChunkedInput) m);
            }

            // Create exception
            if (cause == null) {
                cause = new IOException("Unable to flush event, discarding");
            }
            currentEvent.getFuture().setFailure(cause);
            fireExceptionCaught = true;
        }

        if (fireExceptionCaught) {
            fireExceptionCaughtLater(ctx.getChannel(), cause);
        }
    }
}
