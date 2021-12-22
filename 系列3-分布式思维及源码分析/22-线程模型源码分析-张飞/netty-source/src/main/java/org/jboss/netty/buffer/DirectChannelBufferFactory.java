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
package org.jboss.netty.buffer;

import java.lang.ref.ReferenceQueue;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A {@link ChannelBufferFactory} which pre-allocates a large chunk of direct
 * buffer and returns its slice on demand.  Direct buffers are reclaimed via
 * {@link ReferenceQueue} in most JDK implementations, and therefore they are
 * deallocated less efficiently than an ordinary heap buffer.  Consequently,
 * a user will get {@link OutOfMemoryError} when one tries to allocate small
 * direct buffers more often than the GC throughput of direct buffers, which
 * is much lower than the GC throughput of heap buffers.  This factory avoids
 * this problem by allocating a large chunk of pre-allocated direct buffer and
 * reducing the number of the garbage collected internal direct buffer objects.
 */
public class DirectChannelBufferFactory extends AbstractChannelBufferFactory {

    private static final DirectChannelBufferFactory INSTANCE_BE =
        new DirectChannelBufferFactory(ByteOrder.BIG_ENDIAN);

    private static final DirectChannelBufferFactory INSTANCE_LE =
        new DirectChannelBufferFactory(ByteOrder.LITTLE_ENDIAN);

    public static ChannelBufferFactory getInstance() {
        return INSTANCE_BE;
    }

    public static ChannelBufferFactory getInstance(ByteOrder defaultEndianness) {
        if (defaultEndianness == ByteOrder.BIG_ENDIAN) {
            return INSTANCE_BE;
        } else if (defaultEndianness == ByteOrder.LITTLE_ENDIAN) {
            return INSTANCE_LE;
        } else if (defaultEndianness == null) {
            throw new NullPointerException("defaultEndianness");
        } else {
            throw new IllegalStateException("Should not reach here");
        }
    }

    private final Object bigEndianLock = new Object();
    private final Object littleEndianLock = new Object();
    private final int preallocatedBufCapacity;
    private ChannelBuffer preallocatedBEBuf;
    private int preallocatedBEBufPos;
    private ChannelBuffer preallocatedLEBuf;
    private int preallocatedLEBufPos;

    /**
     * Creates a new factory whose default {@link ByteOrder} is
     * {@link ByteOrder#BIG_ENDIAN}.
     */
    public DirectChannelBufferFactory() {
        this(ByteOrder.BIG_ENDIAN);
    }

    /**
     * Creates a new factory whose default {@link ByteOrder} is
     * {@link ByteOrder#BIG_ENDIAN}.
     */
    public DirectChannelBufferFactory(int preallocatedBufferCapacity) {
        this(ByteOrder.BIG_ENDIAN, preallocatedBufferCapacity);
    }

    /**
     * Creates a new factory with the specified default {@link ByteOrder}.
     *
     * @param defaultOrder the default {@link ByteOrder} of this factory
     */
    public DirectChannelBufferFactory(ByteOrder defaultOrder) {
        this(defaultOrder, 1048576);
    }

    /**
     * Creates a new factory with the specified default {@link ByteOrder}.
     *
     * @param defaultOrder the default {@link ByteOrder} of this factory
     */
    public DirectChannelBufferFactory(ByteOrder defaultOrder, int preallocatedBufferCapacity) {
        super(defaultOrder);
        if (preallocatedBufferCapacity <= 0) {
            throw new IllegalArgumentException(
                    "preallocatedBufCapacity must be greater than 0: " + preallocatedBufferCapacity);
        }

        preallocatedBufCapacity = preallocatedBufferCapacity;
    }

    public ChannelBuffer getBuffer(ByteOrder order, int capacity) {
        if (order == null) {
            throw new NullPointerException("order");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }
        if (capacity == 0) {
            return ChannelBuffers.EMPTY_BUFFER;
        }
        if (capacity >= preallocatedBufCapacity) {
            return ChannelBuffers.directBuffer(order, capacity);
        }

        ChannelBuffer slice;
        if (order == ByteOrder.BIG_ENDIAN) {
            slice = allocateBigEndianBuffer(capacity);
        } else {
            slice = allocateLittleEndianBuffer(capacity);
        }
        slice.clear();
        return slice;
    }

    public ChannelBuffer getBuffer(ByteOrder order, byte[] array, int offset, int length) {
        if (array == null) {
            throw new NullPointerException("array");
        }
        if (offset < 0) {
            throw new IndexOutOfBoundsException("offset: " + offset);
        }
        if (length == 0) {
            return ChannelBuffers.EMPTY_BUFFER;
        }
        if (offset + length > array.length) {
            throw new IndexOutOfBoundsException("length: " + length);
        }

        ChannelBuffer buf = getBuffer(order, length);
        buf.writeBytes(array, offset, length);
        return buf;
    }

    public ChannelBuffer getBuffer(ByteBuffer nioBuffer) {
        if (!nioBuffer.isReadOnly() && nioBuffer.isDirect()) {
            return ChannelBuffers.wrappedBuffer(nioBuffer);
        }

        ChannelBuffer buf = getBuffer(nioBuffer.order(), nioBuffer.remaining());
        int pos = nioBuffer.position();
        buf.writeBytes(nioBuffer);
        nioBuffer.position(pos);
        return buf;
    }

    private ChannelBuffer allocateBigEndianBuffer(int capacity) {
        ChannelBuffer slice;
        synchronized (bigEndianLock) {
            if (preallocatedBEBuf == null) {
                preallocatedBEBuf = ChannelBuffers.directBuffer(ByteOrder.BIG_ENDIAN, preallocatedBufCapacity);
                slice = preallocatedBEBuf.slice(0, capacity);
                preallocatedBEBufPos = capacity;
            } else if (preallocatedBEBuf.capacity() - preallocatedBEBufPos >= capacity) {
                slice = preallocatedBEBuf.slice(preallocatedBEBufPos, capacity);
                preallocatedBEBufPos += capacity;
            } else {
                preallocatedBEBuf = ChannelBuffers.directBuffer(ByteOrder.BIG_ENDIAN, preallocatedBufCapacity);
                slice = preallocatedBEBuf.slice(0, capacity);
                preallocatedBEBufPos = capacity;
            }
        }
        return slice;
    }

    private ChannelBuffer allocateLittleEndianBuffer(int capacity) {
        ChannelBuffer slice;
        synchronized (littleEndianLock) {
            if (preallocatedLEBuf == null) {
                preallocatedLEBuf = ChannelBuffers.directBuffer(ByteOrder.LITTLE_ENDIAN, preallocatedBufCapacity);
                slice = preallocatedLEBuf.slice(0, capacity);
                preallocatedLEBufPos = capacity;
            } else if (preallocatedLEBuf.capacity() - preallocatedLEBufPos >= capacity) {
                slice = preallocatedLEBuf.slice(preallocatedLEBufPos, capacity);
                preallocatedLEBufPos += capacity;
            } else {
                preallocatedLEBuf = ChannelBuffers.directBuffer(ByteOrder.LITTLE_ENDIAN, preallocatedBufCapacity);
                slice = preallocatedLEBuf.slice(0, capacity);
                preallocatedLEBufPos = capacity;
            }
        }
        return slice;
    }
}
