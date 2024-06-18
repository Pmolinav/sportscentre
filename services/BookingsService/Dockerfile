FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY ../.. /app

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/target

COPY --from=builder $TARGET_FOLDER/ConfigurationBOApi-0.0.1.jar .

ARG PORT_APP=8001

ENV PORT $PORT_APP
EXPOSE $PORT

CMD ["java", "-jar", "ConfigurationBOApi-0.0.1.jar"]