FROM hub.mooc.com:8080/micro-service/openjdk:7-jre
MAINTAINER xxx xxx@imooc.com

COPY target/course-edge-service-1.0-SNAPSHOT.jar /course-edge-service.jar

ENTRYPOINT ["java", "-jar", "/course-edge-service.jar"]
