FROM hub.mooc.com:8080/micro-service/openjdk:7-jre
MAINTAINER xxx xxx@imooc.com

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]
