FROM maven:3.6.3-ibmjava-8-alpine AS appServer

LABEL maintainer="TimXIWS"

WORKDIR /usr/src/carservice
COPY . .
RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
EXPOSE 8080
ENV LOG_STORAGE /var/log/web-traffic.log
VOLUME /var/log


CMD ["java", "-jar", "carservice.jar"]
