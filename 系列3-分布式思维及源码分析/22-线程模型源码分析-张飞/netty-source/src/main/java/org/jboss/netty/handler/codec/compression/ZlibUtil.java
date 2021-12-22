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
package org.jboss.netty.handler.codec.compression;

import org.jboss.netty.util.internal.jzlib.JZlib;
import org.jboss.netty.util.internal.jzlib.ZStream;

/**
 * Utility methods used by {@link ZlibEncoder} and {@link ZlibDecoder}.
 */
final class ZlibUtil {

    static void fail(ZStream z, String message, int resultCode) {
        throw exception(z, message, resultCode);
    }

    static CompressionException exception(ZStream z, String message, int resultCode) {
        return new CompressionException(message + " (" + resultCode + ')' +
                (z.msg != null? ": " + z.msg : ""));
    }

    static Enum<?> convertWrapperType(ZlibWrapper wrapper) {
        Enum<?> convertedWrapperType;
        switch (wrapper) {
        case NONE:
            convertedWrapperType = JZlib.W_NONE;
            break;
        case ZLIB:
            convertedWrapperType = JZlib.W_ZLIB;
            break;
        case GZIP:
            convertedWrapperType = JZlib.W_GZIP;
            break;
        case ZLIB_OR_NONE:
            convertedWrapperType = JZlib.W_ZLIB_OR_NONE;
            break;
        default:
            throw new Error();
        }
        return convertedWrapperType;
    }

    static int wrapperOverhead(ZlibWrapper wrapper) {
        int overhead;
        switch (wrapper) {
        case NONE:
            overhead = 0;
            break;
        case ZLIB:
        case ZLIB_OR_NONE:
            overhead = 2;
            break;
        case GZIP:
            overhead = 10;
            break;
        default:
            throw new Error();
        }
        return overhead;
    }

    private ZlibUtil() {
    }
}
