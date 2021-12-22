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
package org.jboss.netty.util;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelSink;
import org.jboss.netty.util.internal.SystemPropertyUtil;

/**
 * Determines if Netty is running in a debug mode or not.  Please note that
 * this is not a Java debug mode.  You can enable Netty debug mode by
 * specifying the {@code "org.jboss.netty.debug"} system property (e.g.
 * {@code java -Dorg.jboss.netty.debug ...})
 * <p>
 * If debug mode is disabled (default), the stack trace of the exceptions are
 * compressed to help debugging a user application.
 * <p>
 * If debug mode is enabled, the stack trace of the exceptions raised in
 * {@link ChannelPipeline} or {@link ChannelSink} are retained as it is to help
 * debugging Netty.
 */
public final class DebugUtil {

    private static final boolean DEBUG_ENABLED =
            SystemPropertyUtil.getBoolean("org.jboss.netty.debug", false);

    /**
     * Returns {@code true} if and only if Netty debug mode is enabled.
     */
    public static boolean isDebugEnabled() {
        return DEBUG_ENABLED;
    }

    private DebugUtil() {
        // Unused
    }
}
