#!/usr/bin/env bash
thrift --gen py -out ../ message.thrift

thrift --gen java -out ../../message-thrift-service-api/src/main/java message.thrift

