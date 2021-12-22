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
package org.jboss.netty.channel;

import static org.jboss.netty.channel.Channels.*;

/**
 * A skeletal {@link ChannelSink} implementation.
 */
public abstract class AbstractChannelSink implements ChannelSink {

    /**
     * Creates a new instance.
     */
    protected AbstractChannelSink() {
    }

    /**
     * Sends an {@link ExceptionEvent} upstream with the specified
     * {@code cause}.
     *
     * @param event the {@link ChannelEvent} which caused a
     *              {@link ChannelHandler} to raise an exception
     * @param cause the exception raised by a {@link ChannelHandler}
     */
    public void exceptionCaught(ChannelPipeline pipeline,
            ChannelEvent event, ChannelPipelineException cause) throws Exception {
        Throwable actualCause = cause.getCause();
        if (actualCause == null) {
            actualCause = cause;
        }
        if (isFireExceptionCaughtLater(event, actualCause)) {
            fireExceptionCaughtLater(event.getChannel(), actualCause);
        } else {
            fireExceptionCaught(event.getChannel(), actualCause);
        }
    }

    /**
     * Returns {@code true} if and only if the specified {@code actualCause}, which was raised while
     * handling the specified {@code event}, must trigger an {@code exceptionCaught()} event in
     * an I/O thread.
     *
     * @param event the event which raised exception
     * @param actualCause the raised exception
     */
    protected boolean isFireExceptionCaughtLater(ChannelEvent event, Throwable actualCause) {
        return false;
    }

    /**
     * This implementation just directly call {@link Runnable#run()}.
     * Sub-classes should override this if they can handle it in a better way
     */
    public ChannelFuture execute(ChannelPipeline pipeline, Runnable task) {
        try {
            task.run();
            return succeededFuture(pipeline.getChannel());
        } catch (Throwable t) {
            return failedFuture(pipeline.getChannel(), t);
        }
    }
}
