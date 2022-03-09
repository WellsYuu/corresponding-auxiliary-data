FROM hub.mooc.com:8080/micro-service/python-base:latest
MAINTAINER xxx xxx@imooc.com

ENV PYTHONPATH /
COPY message /message

ENTRYPOINT ["python", "/message/message_service.py"]