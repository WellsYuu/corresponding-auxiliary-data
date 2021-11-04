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
package org.apache.coyote.http11.upgrade;

import java.nio.ByteBuffer;

import org.apache.coyote.UpgradeToken;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.NioChannel;
import org.apache.tomcat.util.net.NioSelectorPool;
import org.apache.tomcat.util.net.SocketWrapper;

public class NioProcessor extends AbstractProcessor<NioChannel> {

    private static final Log log = LogFactory.getLog(NioProcessor.class);
    @Override
    protected Log getLog() {return log;}

    private static final int INFINITE_TIMEOUT = -1;

    public NioProcessor(SocketWrapper<NioChannel> wrapper, ByteBuffer leftoverInput,
            UpgradeToken upgradeToken, NioSelectorPool pool,
            int asyncWriteBufferSize) {
        super(upgradeToken,
                new NioServletInputStream(wrapper, pool),
                new NioServletOutputStream(wrapper, asyncWriteBufferSize, pool));

        wrapper.setTimeout(INFINITE_TIMEOUT);
        if (leftoverInput != null) {
            ByteBuffer readBuffer = wrapper.getSocket().getBufHandler().getReadBuffer();
            if (readBuffer.remaining() > 0) {
                readBuffer.flip();
            } else {
                readBuffer.clear();
            }
            readBuffer.put(leftoverInput);
            readBuffer.flip();
        }
    }
}
