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
package org.jboss.netty.util.internal;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Shuts down a list of {@link Executor}s.  {@link #terminate(Executor...)} will
 * shut down all specified {@link ExecutorService}s immediately and wait for
 * their termination.  An {@link Executor} which is not an {@link ExecutorService}
 * will be ignored silently.
 */
public final class ExecutorUtil {

    /**
     * Try to call {@link ExecutorService#shutdownNow()}
     */
    public static void shutdownNow(Executor executor) {
        if (executor instanceof ExecutorService) {
            ExecutorService es = (ExecutorService) executor;
            try {
                es.shutdownNow();
            } catch (SecurityException ex) {
                // Running in a restricted environment - fall back.
                try {
                    es.shutdown();
                } catch (SecurityException ex2) {
                    // Running in a more restricted environment.
                    // Can't shut down this executor - skip to the next.
                } catch (NullPointerException ex2) {
                    // Some JDK throws NPE here, but shouldn't.
                }
            } catch (NullPointerException ex) {
                // Some JDK throws NPE here, but shouldn't.
            }
        }
    }

    /**
     * Returns {@code true} if and only if the specified {@code executor}
     * is an {@link ExecutorService} and is shut down.  Please note that this
     * method returns {@code false} if the specified {@code executor} is not an
     * {@link ExecutorService}.
     */
    public static boolean isShutdown(Executor executor) {
        if (executor instanceof ExecutorService) {
            if (((ExecutorService) executor).isShutdown()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shuts down the specified executors.
     */
    public static void terminate(Executor... executors) {
        terminate(DeadLockProofWorker.PARENT, executors);
    }
    /**
     * Shuts down the specified executors using the given {@link ThreadLocal} to check if there is a deadlock
     */
    public static void terminate(ThreadLocal<Executor> deadLockChecker, Executor... executors) {
        // Check nulls.
        if (executors == null) {
            throw new NullPointerException("executors");
        }

        Executor[] executorsCopy = new Executor[executors.length];
        for (int i = 0; i < executors.length; i ++) {
            if (executors[i] == null) {
                throw new NullPointerException("executors[" + i + ']');
            }
            executorsCopy[i] = executors[i];
        }

        // Check dead lock.
        final Executor currentParent = deadLockChecker.get();
        if (currentParent != null) {
            for (Executor e: executorsCopy) {
                if (e == currentParent) {
                    throw new IllegalStateException(
                            "An Executor cannot be shut down from the thread " +
                            "acquired from itself.  Please make sure you are " +
                            "not calling releaseExternalResources() from an " +
                            "I/O worker thread.");
                }
            }
        }

        // Shut down all executors.
        boolean interrupted = false;
        for (Executor e: executorsCopy) {
            if (!(e instanceof ExecutorService)) {
                continue;
            }

            ExecutorService es = (ExecutorService) e;
            for (;;) {
                shutdownNow(es);

                try {
                    if (es.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                        break;
                    }
                } catch (InterruptedException ex) {
                    interrupted = true;
                }
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }

    private ExecutorUtil() {
    }
}
