#!/bin/bash 
sudo docker run -d -p 10911:10911 -p 10909:10909 --name rmqbroker apache/incubator-rocketmq-broker:4.0.0-incubating
