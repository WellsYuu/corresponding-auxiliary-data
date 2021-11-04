/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.coyote;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.net.SSLSupport;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * Common interface for processors of all protocols.
 */
public interface Processor<S> {
    Executor getExecutor();

    SocketState process(SocketWrapper<S> socketWrapper) throws IOException;

    SocketState event(SocketStatus status) throws IOException;

    SocketState asyncDispatch(SocketStatus status);
    SocketState asyncPostProcess();

    UpgradeToken getUpgradeToken();
    SocketState upgradeDispatch(SocketStatus status) throws IOException;

    void errorDispatch();

    boolean isComet();
    boolean isAsync();
    boolean isUpgrade();

    Request getRequest();

    void recycle(boolean socketClosing);

    void setSslSupport(SSLSupport sslSupport);

    /**
     * Allows retrieving additional input during the upgrade process
     * @return leftover bytes
     */
    ByteBuffer getLeftoverInput();
}
