#!/bin/bash
#
# Copyright 2022 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

sudo docker build -t apache/incubator-rocketmq-namesrv:4.0.0-incubating ./namesrv

sudo docker build -t apache/incubator-rocketmq-broker:4.0.0-incubating ./broker

sudo docker run -d -p 9876:9876 --name rmqnamesrv  apache/incubator-rocketmq-namesrv:4.0.0-incubating

sudo docker run -d -p 10911:10911 -p 10909:10909 --name rmqbroker --link rmqnamesrv:rmqnamesrv -e "NAMESRV_ADDR=rmqnamesrv:9876" apache/incubator-rocketmq-broker:4.0.0-incubating
