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

import java.util.concurrent.Executor;


/**
 *抽象协议实现，包括线程等。
 *处理器是单线程的，并且特定于基于流的协议，
 *这是由一个连接器实现的主要接口
 *适配器是由一个servlet实现的主要接口
 */
public interface ProtocolHandler {

    /**
     * The adapter, used to call the connector.
     */
    public void setAdapter(Adapter adapter);
    public Adapter getAdapter();


    /**
     * The executor, provide access to the underlying thread pool.
     */
    public Executor getExecutor();


    /**
     * Initialise the protocol.
     */
    public void init() throws Exception;


    /**
     * Start the protocol.
     */
    public void start() throws Exception;


    /**
     * Pause the protocol (optional).
     */
    public void pause() throws Exception;


    /**
     * Resume the protocol (optional).
     */
    public void resume() throws Exception;


    /**
     * Stop the protocol.
     */
    public void stop() throws Exception;


    /**
     * Destroy the protocol (optional).
     */
    public void destroy() throws Exception;


    /**
     * Requires APR/native library
     */
    public boolean isAprRequired();


    /**
     * Does this ProtocolHandler support Comet?
     */
    public boolean isCometSupported();


    /**
     * Does this ProtocolHandler support Comet timeouts?
     */
    public boolean isCometTimeoutSupported();


    /**
     * Does this ProtocolHandler support sendfile?
     */
    public boolean isSendfileSupported();
}
