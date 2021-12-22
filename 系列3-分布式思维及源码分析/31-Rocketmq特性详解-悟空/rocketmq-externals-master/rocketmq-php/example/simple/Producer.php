<?php
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
use RocketMQ\Client\Producer\DefaultMQProducer;
use RocketMQ\Common\Message\Message;

$producer = new DefaultMQProducer("ProducerGroupName");
$producer->start();

for ($i = 0; $i < 10000000; $i++) {
    try {
        $msg = new Message("TopicTest", 
        "TagA",
        "OrderID188",
        "Hello world");
        $sendResult = $producer->send($msg);
        echo $sendResult;
    } catch (\Exception $e) {
        echo $e->getMessage() . PHP_EOL . $e->getTraceAsString();
    }
}
$producer->shutdown();