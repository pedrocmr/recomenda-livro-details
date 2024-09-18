# Etapa de build
FROM gradle:7.6.1-jdk17 AS build

WORKDIR /app

COPY build.gradle /app/
COPY settings.gradle /app/
COPY gradle /app/gradle

COPY src /app/src

RUN gradle build --no-daemon


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
