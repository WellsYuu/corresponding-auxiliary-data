#!/usr/bin/env bash

mvn package

docker build -t hub.mooc.com:8080/micro-service/api-gateway-zuul:latest .

docker push hub.mooc.com:8080/micro-service/api-gateway-zuul:latest