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
package org.jboss.netty.handler.codec.http;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.compression.JdkZlibEncoder;
import org.jboss.netty.handler.codec.compression.ZlibEncoder;
import org.jboss.netty.handler.codec.compression.ZlibWrapper;
import org.jboss.netty.handler.codec.embedder.EncoderEmbedder;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.internal.DetectionUtil;
import org.jboss.netty.util.internal.StringUtil;
import org.jboss.netty.util.internal.SystemPropertyUtil;

/**
 * Compresses an {@link HttpMessage} and an {@link HttpChunk} in {@code gzip} or
 * {@code deflate} encoding while respecting the {@code "Accept-Encoding"} header.
 * If there is no matching encoding, no compression is done.  For more
 * information on how this handler modifies the message, please refer to
 * {@link HttpContentEncoder}.
 */
public class HttpContentCompressor extends HttpContentEncoder {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HttpContentCompressor.class);

    private static final int DEFAULT_JDK_WINDOW_SIZE = 15;
    private static final int DEFAULT_JDK_MEM_LEVEL = 8;

    private static final boolean noJdkZlibEncoder;

    static {
        noJdkZlibEncoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibEncoder", false);
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noJdkZlibEncoder: " + noJdkZlibEncoder);
        }
    }

    private final int compressionLevel;
    private final int windowBits;
    private final int memLevel;

    /**
     * Creates a new handler with the default compression level (<tt>6</tt>), default window size
     * ({@value #DEFAULT_JDK_WINDOW_SIZE}) and default memory level ({@value #DEFAULT_JDK_MEM_LEVEL}).
     */
    public HttpContentCompressor() {
        this(6);
    }

    /**
     * Creates a new handler with the specified compression level, default window size
     * ({@value #DEFAULT_JDK_WINDOW_SIZE}) and default memory level ({@value #DEFAULT_JDK_MEM_LEVEL}).
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     */
    public HttpContentCompressor(int compressionLevel) {
        this(compressionLevel, DEFAULT_JDK_WINDOW_SIZE, DEFAULT_JDK_MEM_LEVEL);
    }

    /**
     * Creates a new handler with the specified compression level, window size, and memory level.
     *
     * @param compressionLevel
     *        {@code 1} yields the fastest compression and {@code 9} yields the
     *        best compression.  {@code 0} means no compression.  The default
     *        compression level is {@code 6}.
     * @param windowBits
     *        The base two logarithm of the size of the history buffer.  The
     *        value should be in the range {@code 9} to {@code 15} inclusive.
     *        Larger values result in better compression at the expense of
     *        memory usage.  The default value is {@value #DEFAULT_JDK_WINDOW_SIZE}.
     * @param memLevel
     *        How much memory should be allocated for the internal compression
     *        state.  {@code 1} uses minimum memory and {@code 9} uses maximum
     *        memory.  Larger values result in better and faster compression
     *        at the expense of memory usage.  The default value is {@value #DEFAULT_JDK_MEM_LEVEL}.
     */
    public HttpContentCompressor(int compressionLevel, int windowBits, int memLevel) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException(
                    "compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException(
                    "windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException(
                    "memLevel: " + memLevel + " (expected: 1-9)");
        }
        this.compressionLevel = compressionLevel;
        this.windowBits = windowBits;
        this.memLevel = memLevel;
    }

    @Override
    protected EncoderEmbedder<ChannelBuffer> newContentEncoder(
            HttpMessage msg, String acceptEncoding) throws Exception {
        String contentEncoding = msg.headers().get(HttpHeaders.Names.CONTENT_ENCODING);
        if (contentEncoding != null &&
            !HttpHeaders.Values.IDENTITY.equalsIgnoreCase(contentEncoding)) {
            // Encoded already.
            return null;
        }

        ZlibWrapper wrapper = determineWrapper(acceptEncoding);
        if (wrapper == null) {
            return null;
        }

        if (DetectionUtil.javaVersion() < 7 || noJdkZlibEncoder ||
            windowBits != DEFAULT_JDK_WINDOW_SIZE || memLevel != DEFAULT_JDK_MEM_LEVEL) {
            return new EncoderEmbedder<ChannelBuffer>(
                    new ZlibEncoder(wrapper, compressionLevel, windowBits, memLevel));
        } else {
            return new EncoderEmbedder<ChannelBuffer>(
                    new JdkZlibEncoder(wrapper, compressionLevel));
        }
    }

    @Override
    protected String getTargetContentEncoding(String acceptEncoding) throws Exception {
        ZlibWrapper wrapper = determineWrapper(acceptEncoding);
        if (wrapper == null) {
            return null;
        }

        switch (wrapper) {
        case GZIP:
            return "gzip";
        case ZLIB:
            return "deflate";
        default:
            throw new Error();
        }
    }

    private static ZlibWrapper determineWrapper(String acceptEncoding) {
        float starQ = -1.0f;
        float gzipQ = -1.0f;
        float deflateQ = -1.0f;
        for (String encoding: StringUtil.split(acceptEncoding, ',')) {
            float q = 1.0f;
            int equalsPos = encoding.indexOf('=');
            if (equalsPos != -1) {
                try {
                    q = Float.valueOf(encoding.substring(equalsPos + 1));
                } catch (NumberFormatException e) {
                    // Ignore encoding
                    q = 0.0f;
                }
            }
            if (encoding.indexOf('*') >= 0) {
                starQ = q;
            } else if (encoding.contains("gzip") && q > gzipQ) {
                gzipQ = q;
            } else if (encoding.contains("deflate") && q > deflateQ) {
                deflateQ = q;
            }
        }
        if (gzipQ > 0.0f || deflateQ > 0.0f) {
            if (gzipQ >= deflateQ) {
                return ZlibWrapper.GZIP;
            } else {
                return ZlibWrapper.ZLIB;
            }
        }
        if (starQ > 0.0f) {
            if (gzipQ == -1.0f) {
                return ZlibWrapper.GZIP;
            }
            if (deflateQ == -1.0f) {
                return ZlibWrapper.ZLIB;
            }
        }
        return null;
    }
}
