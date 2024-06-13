FROM openjdk:21-slim as build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src
RUN ./gradlew build

FROM openjdk:21-slim

COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]