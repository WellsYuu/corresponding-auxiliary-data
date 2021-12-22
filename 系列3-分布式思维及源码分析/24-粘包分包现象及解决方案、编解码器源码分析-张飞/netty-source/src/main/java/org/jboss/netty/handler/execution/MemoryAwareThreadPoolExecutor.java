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
package org.jboss.netty.handler.execution;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.DefaultObjectSizeEstimator;
import org.jboss.netty.util.ObjectSizeEstimator;
import org.jboss.netty.util.internal.ConcurrentIdentityHashMap;
import org.jboss.netty.util.internal.SharedResourceMisuseDetector;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A {@link ThreadPoolExecutor} which blocks the task submission when there's
 * too many tasks in the queue.  Both per-{@link Channel} and per-{@link Executor}
 * limitation can be applied.
 * <p>
 * When a task (i.e. {@link Runnable}) is submitted,
 * {@link MemoryAwareThreadPoolExecutor} calls {@link ObjectSizeEstimator#estimateSize(Object)}
 * to get the estimated size of the task in bytes to calculate the amount of
 * memory occupied by the unprocessed tasks.
 * <p>
 * If the total size of the unprocessed tasks exceeds either per-{@link Channel}
 * or per-{@link Executor} threshold, any further {@link #execute(Runnable)}
 * call will block until the tasks in the queue are processed so that the total
 * size goes under the threshold.
 *
 * <h3>Using an alternative task size estimation strategy</h3>
 *
 * Although the default implementation does its best to guess the size of an
 * object of unknown type, it is always good idea to to use an alternative
 * {@link ObjectSizeEstimator} implementation instead of the
 * {@link DefaultObjectSizeEstimator} to avoid incorrect task size calculation,
 * especially when:
 * <ul>
 *   <li>you are using {@link MemoryAwareThreadPoolExecutor} independently from
 *       {@link ExecutionHandler},</li>
 *   <li>you are submitting a task whose type is not {@link ChannelEventRunnable}, or</li>
 *   <li>the message type of the {@link MessageEvent} in the {@link ChannelEventRunnable}
 *       is not {@link ChannelBuffer}.</li>
 * </ul>
 * Here is an example that demonstrates how to implement an {@link ObjectSizeEstimator}
 * which understands a user-defined object:
 * <pre>
 * public class MyRunnable implements {@link Runnable} {
 *
 *     <b>private final byte[] data;</b>
 *
 *     public MyRunnable(byte[] data) {
 *         this.data = data;
 *     }
 *
 *     public void run() {
 *         // Process 'data' ..
 *     }
 * }
 *
 * public class MyObjectSizeEstimator extends {@link DefaultObjectSizeEstimator} {
 *
 *     {@literal @Override}
 *     public int estimateSize(Object o) {
 *         if (<b>o instanceof MyRunnable</b>) {
 *             <b>return ((MyRunnable) o).data.length + 8;</b>
 *         }
 *         return super.estimateSize(o);
 *     }
 * }
 *
 * {@link ThreadPoolExecutor} pool = new {@link MemoryAwareThreadPoolExecutor}(
 *         16, 65536, 1048576, 30, {@link TimeUnit}.SECONDS,
 *         <b>new MyObjectSizeEstimator()</b>,
 *         {@link Executors}.defaultThreadFactory());
 *
 * <b>pool.execute(new MyRunnable(data));</b>
 * </pre>
 *
 * <h3>Event execution order</h3>
 *
 * Please note that this executor does not maintain the order of the
 * {@link ChannelEvent}s for the same {@link Channel}.  For example,
 * you can even receive a {@code "channelClosed"} event before a
 * {@code "messageReceived"} event, as depicted by the following diagram.
 *
 * For example, the events can be processed as depicted below:
 *
 * <pre>
 *           --------------------------------&gt; Timeline --------------------------------&gt;
 *
 * Thread X: --- Channel A (Event 1) --- Channel A (Event 2) ---------------------------&gt;
 *
 * Thread Y: --- Channel A (Event 3) --- Channel B (Event 2) --- Channel B (Event 3) ---&gt;
 *
 * Thread Z: --- Channel B (Event 1) --- Channel B (Event 4) --- Channel A (Event 4) ---&gt;
 * </pre>
 *
 * To maintain the event order, you must use {@link OrderedMemoryAwareThreadPoolExecutor}.
 *
 * @apiviz.has org.jboss.netty.util.ObjectSizeEstimator oneway - -
 * @apiviz.has org.jboss.netty.handler.execution.ChannelEventRunnable oneway - - executes
 */
public class MemoryAwareThreadPoolExecutor extends ThreadPoolExecutor {

    private static final InternalLogger logger =
        InternalLoggerFactory.getInstance(MemoryAwareThreadPoolExecutor.class);

    private static final SharedResourceMisuseDetector misuseDetector =
        new SharedResourceMisuseDetector(MemoryAwareThreadPoolExecutor.class);

    private volatile Settings settings;

    private final ConcurrentMap<Channel, AtomicLong> channelCounters =
        new ConcurrentIdentityHashMap<Channel, AtomicLong>();
    private final Limiter totalLimiter;

    private volatile boolean notifyOnShutdown;

    /**
     * Creates a new instance.
     *
     * @param corePoolSize          the maximum number of active threads
     * @param maxChannelMemorySize  the maximum total size of the queued events per channel.
     *                              Specify {@code 0} to disable.
     * @param maxTotalMemorySize    the maximum total size of the queued events for this pool
     *                              Specify {@code 0} to disable.
     */
    public MemoryAwareThreadPoolExecutor(
            int corePoolSize, long maxChannelMemorySize, long maxTotalMemorySize) {

        this(corePoolSize, maxChannelMemorySize, maxTotalMemorySize, 30, TimeUnit.SECONDS);
    }

    /**
     * Creates a new instance.
     *
     * @param corePoolSize          the maximum number of active threads
     * @param maxChannelMemorySize  the maximum total size of the queued events per channel.
     *                              Specify {@code 0} to disable.
     * @param maxTotalMemorySize    the maximum total size of the queued events for this pool
     *                              Specify {@code 0} to disable.
     * @param keepAliveTime         the amount of time for an inactive thread to shut itself down
     * @param unit                  the {@link TimeUnit} of {@code keepAliveTime}
     */
    public MemoryAwareThreadPoolExecutor(
            int corePoolSize, long maxChannelMemorySize, long maxTotalMemorySize,
            long keepAliveTime, TimeUnit unit) {

        this(
                corePoolSize, maxChannelMemorySize, maxTotalMemorySize, keepAliveTime, unit,
                Executors.defaultThreadFactory());
    }

    /**
     * Creates a new instance.
     *
     * @param corePoolSize          the maximum number of active threads
     * @param maxChannelMemorySize  the maximum total size of the queued events per channel.
     *                              Specify {@code 0} to disable.
     * @param maxTotalMemorySize    the maximum total size of the queued events for this pool
     *                              Specify {@code 0} to disable.
     * @param keepAliveTime         the amount of time for an inactive thread to shut itself down
     * @param unit                  the {@link TimeUnit} of {@code keepAliveTime}
     * @param threadFactory         the {@link ThreadFactory} of this pool
     */
    public MemoryAwareThreadPoolExecutor(
            int corePoolSize, long maxChannelMemorySize, long maxTotalMemorySize,
            long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {

        this(
                corePoolSize, maxChannelMemorySize, maxTotalMemorySize, keepAliveTime, unit,
                new DefaultObjectSizeEstimator(), threadFactory);
    }

    /**
     * Creates a new instance.
     *
     * @param corePoolSize          the maximum number of active threads
     * @param maxChannelMemorySize  the maximum total size of the queued events per channel.
     *                              Specify {@code 0} to disable.
     * @param maxTotalMemorySize    the maximum total size of the queued events for this pool
     *                              Specify {@code 0} to disable.
     * @param keepAliveTime         the amount of time for an inactive thread to shut itself down
     * @param unit                  the {@link TimeUnit} of {@code keepAliveTime}
     * @param threadFactory         the {@link ThreadFactory} of this pool
     * @param objectSizeEstimator   the {@link ObjectSizeEstimator} of this pool
     */
    public MemoryAwareThreadPoolExecutor(
            int corePoolSize, long maxChannelMemorySize, long maxTotalMemorySize,
            long keepAliveTime, TimeUnit unit, ObjectSizeEstimator objectSizeEstimator,
            ThreadFactory threadFactory) {

        super(corePoolSize, corePoolSize, keepAliveTime, unit,
              new LinkedBlockingQueue<Runnable>(), threadFactory, new NewThreadRunsPolicy());

        if (objectSizeEstimator == null) {
            throw new NullPointerException("objectSizeEstimator");
        }
        if (maxChannelMemorySize < 0) {
            throw new IllegalArgumentException(
                    "maxChannelMemorySize: " + maxChannelMemorySize);
        }
        if (maxTotalMemorySize < 0) {
            throw new IllegalArgumentException(
                    "maxTotalMemorySize: " + maxTotalMemorySize);
        }

        // Call allowCoreThreadTimeOut(true) using reflection
        // because it is not supported in Java 5.
        try {
            Method m = getClass().getMethod("allowCoreThreadTimeOut", new Class[] { boolean.class });
            m.invoke(this, Boolean.TRUE);
        } catch (Throwable t) {
            // Java 5
            logger.debug(
                    "ThreadPoolExecutor.allowCoreThreadTimeOut() is not " +
                    "supported in this platform.");
        }

        settings = new Settings(
                objectSizeEstimator, maxChannelMemorySize);

        if (maxTotalMemorySize == 0) {
            totalLimiter = null;
        } else {
            totalLimiter = new Limiter(maxTotalMemorySize);
        }

        // Misuse check
        misuseDetector.increase();
    }

    @Override
    protected void terminated() {
        super.terminated();
        misuseDetector.decrease();
    }

    /**
     * This will call {@link #shutdownNow(boolean)} with the value of {@link #getNotifyChannelFuturesOnShutdown()}.
     */
    @Override
    public List<Runnable> shutdownNow() {
        return shutdownNow(notifyOnShutdown);
    }

    /**
     * See {@link ThreadPoolExecutor#shutdownNow()} for how it handles the shutdown.
     * If {@code true} is given to this method it also notifies all {@link ChannelFuture}'s
     * of the not executed {@link ChannelEventRunnable}'s.
     *
     * <p>
     * Be aware that if you call this with {@code false} you will need to handle the
     * notification of the {@link ChannelFuture}'s by your self. So only use this if you
     * really have a use-case for it.
     * </p>
     *
     */
    public List<Runnable> shutdownNow(boolean notify) {
        if (!notify) {
            return super.shutdownNow();
        }
        Throwable cause = null;
        Set<Channel> channels = null;

        List<Runnable> tasks = super.shutdownNow();

        // loop over all tasks and cancel the ChannelFuture of the ChannelEventRunable's
        for (Runnable task: tasks) {
            if (task instanceof ChannelEventRunnable) {
                if (cause == null) {
                    cause = new IOException("Unable to process queued event");
                }
                ChannelEvent event = ((ChannelEventRunnable) task).getEvent();
                event.getFuture().setFailure(cause);

                if (channels == null) {
                    channels = new HashSet<Channel>();
                }

                // store the Channel of the event for later notification of the exceptionCaught event
                channels.add(event.getChannel());
            }
        }

        // loop over all channels and fire an exceptionCaught event
        if (channels != null) {
            for (Channel channel: channels) {
                Channels.fireExceptionCaughtLater(channel, cause);
            }
        }
        return tasks;
    }

    /**
     * Returns the {@link ObjectSizeEstimator} of this pool.
     */
    public ObjectSizeEstimator getObjectSizeEstimator() {
        return settings.objectSizeEstimator;
    }

    /**
     * Sets the {@link ObjectSizeEstimator} of this pool.
     */
    public void setObjectSizeEstimator(ObjectSizeEstimator objectSizeEstimator) {
        if (objectSizeEstimator == null) {
            throw new NullPointerException("objectSizeEstimator");
        }

        settings = new Settings(
                objectSizeEstimator,
                settings.maxChannelMemorySize);
    }

    /**
     * Returns the maximum total size of the queued events per channel.
     */
    public long getMaxChannelMemorySize() {
        return settings.maxChannelMemorySize;
    }

    /**
     * Sets the maximum total size of the queued events per channel.
     * Specify {@code 0} to disable.
     */
    public void setMaxChannelMemorySize(long maxChannelMemorySize) {
        if (maxChannelMemorySize < 0) {
            throw new IllegalArgumentException(
                    "maxChannelMemorySize: " + maxChannelMemorySize);
        }

        if (getTaskCount() > 0) {
            throw new IllegalStateException(
                    "can't be changed after a task is executed");
        }

        settings = new Settings(
                settings.objectSizeEstimator,
                maxChannelMemorySize);
    }

    /**
     * Returns the maximum total size of the queued events for this pool.
     */
    public long getMaxTotalMemorySize() {
        if (totalLimiter == null) {
            return 0;
        }
        return totalLimiter.limit;
    }

    /**
     * If set to {@code false} no queued {@link ChannelEventRunnable}'s {@link ChannelFuture}
     * will get notified once {@link #shutdownNow()} is called.  If set to {@code true} every
     * queued {@link ChannelEventRunnable} will get marked as failed via {@link ChannelFuture#setFailure(Throwable)}.
     *
     * <p>
     * Please only set this to {@code false} if you want to handle the notification by yourself
     * and know what you are doing. Default is {@code true}.
     * </p>
     */
    public void setNotifyChannelFuturesOnShutdown(boolean notifyOnShutdown) {
        this.notifyOnShutdown = notifyOnShutdown;
    }

    /**
     * Returns if the {@link ChannelFuture}'s of the {@link ChannelEventRunnable}'s should be
     * notified about the shutdown of this {@link MemoryAwareThreadPoolExecutor}.
     */
    public boolean getNotifyChannelFuturesOnShutdown() {
        return notifyOnShutdown;
    }

    @Override
    public void execute(Runnable command) {
        if (command instanceof ChannelDownstreamEventRunnable) {
            throw new RejectedExecutionException("command must be enclosed with an upstream event.");
        }
        if (!(command instanceof ChannelEventRunnable)) {
            command = new MemoryAwareRunnable(command);
        }

        increaseCounter(command);
        doExecute(command);
    }

    /**
     * Put the actual execution logic here.  The default implementation simply
     * calls {@link #doUnorderedExecute(Runnable)}.
     */
    protected void doExecute(Runnable task) {
        doUnorderedExecute(task);
    }

    /**
     * Executes the specified task without maintaining the event order.
     */
    protected final void doUnorderedExecute(Runnable task) {
        super.execute(task);
    }

    @Override
    public boolean remove(Runnable task) {
        boolean removed = super.remove(task);
        if (removed) {
            decreaseCounter(task);
        }
        return removed;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        decreaseCounter(r);
    }

    protected void increaseCounter(Runnable task) {
        if (!shouldCount(task)) {
            return;
        }

        Settings settings = this.settings;
        long maxChannelMemorySize = settings.maxChannelMemorySize;

        int increment = settings.objectSizeEstimator.estimateSize(task);

        if (task instanceof ChannelEventRunnable) {
            ChannelEventRunnable eventTask = (ChannelEventRunnable) task;
            eventTask.estimatedSize = increment;
            Channel channel = eventTask.getEvent().getChannel();
            long channelCounter = getChannelCounter(channel).addAndGet(increment);
            //System.out.println("IC: " + channelCounter + ", " + increment);
            if (maxChannelMemorySize != 0 && channelCounter >= maxChannelMemorySize && channel.isOpen()) {
                if (channel.isReadable()) {
                    //System.out.println("UNREADABLE");
                    ChannelHandlerContext ctx = eventTask.getContext();
                    if (ctx.getHandler() instanceof ExecutionHandler) {
                        // readSuspended = true;
                        ctx.setAttachment(Boolean.TRUE);
                    }
                    channel.setReadable(false);
                }
            }
        } else {
            ((MemoryAwareRunnable) task).estimatedSize = increment;
        }

        if (totalLimiter != null) {
            totalLimiter.increase(increment);
        }
    }

    protected void decreaseCounter(Runnable task) {
        if (!shouldCount(task)) {
            return;
        }

        Settings settings = this.settings;
        long maxChannelMemorySize = settings.maxChannelMemorySize;

        int increment;
        if (task instanceof ChannelEventRunnable) {
            increment = ((ChannelEventRunnable) task).estimatedSize;
        } else {
            increment = ((MemoryAwareRunnable) task).estimatedSize;
        }

        if (totalLimiter != null) {
            totalLimiter.decrease(increment);
        }

        if (task instanceof ChannelEventRunnable) {
            ChannelEventRunnable eventTask = (ChannelEventRunnable) task;
            Channel channel = eventTask.getEvent().getChannel();
            long channelCounter = getChannelCounter(channel).addAndGet(-increment);
            //System.out.println("DC: " + channelCounter + ", " + increment);
            if (maxChannelMemorySize != 0 && channelCounter < maxChannelMemorySize && channel.isOpen()) {
                if (!channel.isReadable()) {
                    //System.out.println("READABLE");
                    ChannelHandlerContext ctx = eventTask.getContext();
                    if (ctx.getHandler() instanceof ExecutionHandler) {
                        // check if the attachment was set as this means that we suspend the channel
                        // from reads. This only works when this pool is used with ExecutionHandler
                        // but I guess thats good enough for us.
                        //
                        // See #215
                        if (ctx.getAttachment() != null) {
                            // readSuspended = false;
                            ctx.setAttachment(null);
                            channel.setReadable(true);
                        }
                    } else {
                        channel.setReadable(true);
                    }
                }
            }
        }
    }

    private AtomicLong getChannelCounter(Channel channel) {
        AtomicLong counter = channelCounters.get(channel);
        if (counter == null) {
            counter = new AtomicLong();
            AtomicLong oldCounter = channelCounters.putIfAbsent(channel, counter);
            if (oldCounter != null) {
                counter = oldCounter;
            }
        }

        // Remove the entry when the channel closes.
        if (!channel.isOpen()) {
            channelCounters.remove(channel);
        }
        return counter;
    }

    /**
     * Returns {@code true} if and only if the specified {@code task} should
     * be counted to limit the global and per-channel memory consumption.
     * To override this method, you must call {@code super.shouldCount()} to
     * make sure important tasks are not counted.
     */
    protected boolean shouldCount(Runnable task) {
        if (task instanceof ChannelUpstreamEventRunnable) {
            ChannelUpstreamEventRunnable r = (ChannelUpstreamEventRunnable) task;
            ChannelEvent e = r.getEvent();
            if (e instanceof WriteCompletionEvent) {
                return false;
            } else if (e instanceof ChannelStateEvent) {
                if (((ChannelStateEvent) e).getState() == ChannelState.INTEREST_OPS) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final class Settings {
        final ObjectSizeEstimator objectSizeEstimator;
        final long maxChannelMemorySize;

        Settings(ObjectSizeEstimator objectSizeEstimator,
                 long maxChannelMemorySize) {
            this.objectSizeEstimator = objectSizeEstimator;
            this.maxChannelMemorySize = maxChannelMemorySize;
        }
    }

    private static final class NewThreadRunsPolicy implements RejectedExecutionHandler {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                final Thread t = new Thread(r, "Temporary task executor");
                t.start();
            } catch (Throwable e) {
                throw new RejectedExecutionException(
                        "Failed to start a new thread", e);
            }
        }
    }

    private static final class MemoryAwareRunnable implements Runnable {
        final Runnable task;
        int estimatedSize;

        MemoryAwareRunnable(Runnable task) {
            this.task = task;
        }

        public void run() {
            task.run();
        }
    }

    private static class Limiter {

        final long limit;
        private long counter;
        private int waiters;

        Limiter(long limit) {
            this.limit = limit;
        }

        synchronized void increase(long amount) {
            while (counter >= limit) {
                waiters ++;
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    waiters --;
                }
            }
            counter += amount;
        }

        synchronized void decrease(long amount) {
            counter -= amount;
            if (counter < limit && waiters > 0) {
                notifyAll();
            }
        }
    }
}
