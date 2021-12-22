/*
 * Copyright 2015 The Netty Project
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
package org.jboss.netty.handler.codec.http.cookie;

import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.firstInvalidCookieNameOctet;
import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.firstInvalidCookieValueOctet;
import static org.jboss.netty.handler.codec.http.cookie.CookieUtil.unwrapValue;

import java.nio.CharBuffer;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

/**
 * Parent of Client and Server side cookie decoders
 */
public abstract class CookieDecoder {

    private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

    private final boolean strict;

    protected CookieDecoder(boolean strict) {
        this.strict = strict;
    }

    protected DefaultCookie initCookie(String header, int nameBegin, int nameEnd, int valueBegin, int valueEnd) {
        if (nameBegin == -1 || nameBegin == nameEnd) {
            logger.debug("Skipping cookie with null name");
            return null;
        }

        if (valueBegin == -1) {
            logger.debug("Skipping cookie with null value");
            return null;
        }

        CharSequence wrappedValue = CharBuffer.wrap(header, valueBegin, valueEnd);
        CharSequence unwrappedValue = unwrapValue(wrappedValue);
        if (unwrappedValue == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping cookie because starting quotes are not properly balanced in '"
                    + wrappedValue + "'");
            }
            return null;
        }

        final String name = header.substring(nameBegin, nameEnd);

        int invalidOctetPos;
        if (strict && (invalidOctetPos = firstInvalidCookieNameOctet(name)) >= 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping cookie because name '" + name + "' contains invalid char '"
                    + name.charAt(invalidOctetPos) + "'");
            }
            return null;
        }

        final boolean wrap = unwrappedValue.length() != valueEnd - valueBegin;

        if (strict && (invalidOctetPos = firstInvalidCookieValueOctet(unwrappedValue)) >= 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping cookie because value '" + unwrappedValue
                    + "' contains invalid char '" + unwrappedValue.charAt(invalidOctetPos) + "'");
            }
            return null;
        }

        DefaultCookie cookie = new DefaultCookie(name, unwrappedValue.toString());
        cookie.setWrap(wrap);
        return cookie;
    }
}
