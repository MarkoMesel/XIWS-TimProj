FROM maven:3.6.3-ibmjava-8-alpine AS appServer

LABEL maintainer="mmarkom0001@gmail.com"

WORKDIR /usr/src/schedule
COPY . .
RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
EXPOSE 8084
ENV LOG_STORAGE /var/log/web-traffic.log
VOLUME /var/log
COPY --from=appServer /usr/src/schedule/target/ScheduleService-0.0.1-SNAPSHOT.jar ./

CMD ["java", "-jar", "ScheduleService-0.0.1-SNAPSHOT.jar"]