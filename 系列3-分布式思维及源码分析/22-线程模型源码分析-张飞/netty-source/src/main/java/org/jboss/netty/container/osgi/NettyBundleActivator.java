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
package org.jboss.netty.container.osgi;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.OsgiLoggerFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * An OSGi {@link BundleActivator} that configures logging.
 */
public class NettyBundleActivator implements BundleActivator {

    private OsgiLoggerFactory loggerFactory;

    public void start(BundleContext ctx) throws Exception {
        // Switch the internal logger to the OSGi LogService.
        loggerFactory = new OsgiLoggerFactory(ctx);
        InternalLoggerFactory.setDefaultFactory(loggerFactory);
    }

    public void stop(BundleContext ctx) throws Exception {
        if (loggerFactory != null) {
            InternalLoggerFactory.setDefaultFactory(loggerFactory.getFallback());
            loggerFactory.destroy();
            loggerFactory = null;
        }
    }
}
