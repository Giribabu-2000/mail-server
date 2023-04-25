FROM openjdk:11
LABEL authors="Giribabu S"
MAINTAINER "Giribabu S"
EXPOSE 9001
VOLUME /tmp
COPY target/mail-server-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]