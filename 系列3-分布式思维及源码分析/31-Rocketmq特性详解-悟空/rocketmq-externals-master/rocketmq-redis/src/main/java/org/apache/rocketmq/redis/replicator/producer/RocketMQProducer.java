/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.redis.replicator.producer;

import java.util.Objects;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.redis.replicator.conf.Configure;
import org.apache.rocketmq.redis.replicator.event.Event;
import org.apache.rocketmq.remoting.exception.RemotingException;

import static com.alibaba.fastjson.JSON.toJSONBytes;
import static com.alibaba.fastjson.serializer.SerializerFeature.IgnoreNonFieldGetter;
import static org.apache.rocketmq.redis.replicator.conf.ReplicatorConstants.ROCKETMQ_DATA_TOPIC;
import static org.apache.rocketmq.redis.replicator.conf.ReplicatorConstants.ROCKETMQ_NAMESERVER_ADDRESS;
import static org.apache.rocketmq.redis.replicator.conf.ReplicatorConstants.ROCKETMQ_PRODUCER_GROUP_NAME;

public class RocketMQProducer {

    private String topic;
    private MQProducer producer;

    public RocketMQProducer(Configure configure) throws MQClientException {
        Objects.requireNonNull(configure);
        this.topic = configure.getString(ROCKETMQ_DATA_TOPIC);
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(configure.getString(ROCKETMQ_NAMESERVER_ADDRESS));
        producer.setProducerGroup(configure.getString(ROCKETMQ_PRODUCER_GROUP_NAME));
        this.producer = producer;
        this.producer.start();
    }

    /**
     * @param event redis event.
     * @return true if send success.
     * @throws MQClientException    MQClientException
     * @throws RemotingException    RemotingException
     * @throws InterruptedException InterruptedException
     * @throws MQBrokerException    MQBrokerException
     */
    public boolean send(
        Event event) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        Message msg = new Message(this.topic, toJSONBytes(event, IgnoreNonFieldGetter));
        SendResult rs = this.producer.send(msg);
        return rs.getSendStatus() == SendStatus.SEND_OK;
    }
}
