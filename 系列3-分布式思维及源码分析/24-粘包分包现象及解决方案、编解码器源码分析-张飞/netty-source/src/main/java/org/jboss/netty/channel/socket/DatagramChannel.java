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
package org.jboss.netty.channel.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * A UDP/IP {@link Channel} which is created by {@link DatagramChannelFactory}.
 *
 * @apiviz.landmark
 * @apiviz.composedOf org.jboss.netty.channel.socket.DatagramChannelConfig
 */
public interface DatagramChannel extends Channel {
    DatagramChannelConfig getConfig();
    InetSocketAddress getLocalAddress();
    InetSocketAddress getRemoteAddress();

    /**
     * Joins a multicast group.
     */
    ChannelFuture joinGroup(InetAddress multicastAddress);

    /**
     * Joins the specified multicast group at the specified interface.
     */
    ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface);

    /**
     * Leaves a multicast group.
     */
    ChannelFuture leaveGroup(InetAddress multicastAddress);

    /**
     * Leaves a multicast group on a specified local interface.
     */
    ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface);
}
