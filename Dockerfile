FROM eclipse-temurin:17-jdk-alpine
LABEL authors="lmello"

VOLUME /tmp

ARG DATASOURCE_URL
ARG DATASOURCE_USER
ARG DATASOURCE_PASSWORD

COPY target/*.jar app.jar

ENV DATASOURCE_URL=${DATASOURCE_URL}
ENV DATASOURCE_USER=${DATASOURCE_USER}
ENV DATASOURCE_PASSWORD=${DATASOURCE_PASSWORD}

ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar",  "/app.jar"]