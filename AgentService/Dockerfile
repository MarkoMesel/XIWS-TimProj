FROM maven:3.6.3-ibmjava-8-alpine

LABEL maintainer="TimXIWS"

WORKDIR /usr/src/agentservice
COPY . .
RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
EXPOSE 8080
ENV LOG_STORAGE /var/log/web-traffic.log
VOLUME /var/log
COPY --from=maven:3.6.3-ibmjava-8-alpine /target/agentservice.jar ./

CMD ["java", "-jar", "agentservice.jar"]
