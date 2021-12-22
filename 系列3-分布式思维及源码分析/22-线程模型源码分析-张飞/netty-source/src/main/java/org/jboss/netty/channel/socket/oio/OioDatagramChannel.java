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
package org.jboss.netty.channel.socket.oio;

import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelSink;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.DatagramChannelConfig;
import org.jboss.netty.channel.socket.DefaultDatagramChannelConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;

import static org.jboss.netty.channel.Channels.*;

final class OioDatagramChannel extends AbstractOioChannel
                                implements DatagramChannel {

    final MulticastSocket socket;
    private final DatagramChannelConfig config;

    OioDatagramChannel(
            ChannelFactory factory,
            ChannelPipeline pipeline,
            ChannelSink sink) {

        super(null, factory, pipeline, sink);

        try {
            socket = new MulticastSocket(null);
        } catch (IOException e) {
            throw new ChannelException("Failed to open a datagram socket.", e);
        }

        try {
            socket.setSoTimeout(10);
            socket.setBroadcast(false);
        } catch (SocketException e) {
            throw new ChannelException(
                    "Failed to configure the datagram socket timeout.", e);
        }
        config = new DefaultDatagramChannelConfig(socket);

        fireChannelOpen(this);
    }

    public DatagramChannelConfig getConfig() {
        return config;
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress) {
        ensureBound();
        try {
            socket.joinGroup(multicastAddress);
            return succeededFuture(this);
        } catch (IOException e) {
            return failedFuture(this, e);
        }
    }

    public ChannelFuture joinGroup(
            InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        ensureBound();
        try {
            socket.joinGroup(multicastAddress, networkInterface);
            return succeededFuture(this);
        } catch (IOException e) {
            return failedFuture(this, e);
        }
    }

    private void ensureBound() {
        if (!isBound()) {
            throw new IllegalStateException(
                    DatagramChannel.class.getName() +
                    " must be bound to join a group.");
        }
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress) {
        try {
            socket.leaveGroup(multicastAddress);
            return succeededFuture(this);
        } catch (IOException e) {
            return failedFuture(this, e);
        }
    }

    public ChannelFuture leaveGroup(
            InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        try {
            socket.leaveGroup(multicastAddress, networkInterface);
            return succeededFuture(this);
        } catch (IOException e) {
            return failedFuture(this, e);
        }
    }

    @Override
    boolean isSocketBound() {
        return socket.isBound();
    }

    @Override
    boolean isSocketConnected() {
        return socket.isConnected();
    }

    @Override
    InetSocketAddress getLocalSocketAddress() throws Exception {
        return (InetSocketAddress) socket.getLocalSocketAddress();
    }

    @Override
    InetSocketAddress getRemoteSocketAddress() throws Exception {
        return (InetSocketAddress) socket.getRemoteSocketAddress();
    }

    @Override
    void closeSocket() {
        socket.close();
    }

    @Override
    boolean isSocketClosed() {
        return socket.isClosed();
    }
}
