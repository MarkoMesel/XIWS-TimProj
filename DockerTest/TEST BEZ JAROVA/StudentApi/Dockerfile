FROM maven:3.6.3-ibmjava-8-alpine AS appServer

LABEL maintainer="mmarkom0001@gmail.com"

WORKDIR /usr/src/student
COPY . .
RUN ["mvn", "package", "-DskipTests"]


FROM openjdk:8-jdk-alpine
WORKDIR /app
EXPOSE 8085
ENV LOG_STORAGE /var/log/web-traffic.log
VOLUME /var/log
COPY --from=appServer /usr/src/student/target/StudentApi-0.0.1-SNAPSHOT.jar ./

CMD ["java", "-jar", "StudentApi-0.0.1-SNAPSHOT.jar"]