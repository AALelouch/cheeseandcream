# syntax=docker/dockerfile:1.7

FROM maven:3.9.12-eclipse-temurin-25 AS build

WORKDIR /workspace

COPY pom.xml ./
RUN --mount=type=cache,id=maven-repository,target=/root/.m2 \
    mvn --batch-mode --no-transfer-progress dependency:go-offline

COPY src ./src
RUN --mount=type=cache,id=maven-repository,target=/root/.m2 \
    mvn --batch-mode --no-transfer-progress clean package -DskipTests \
    && cp target/cheeseandcream-*.jar /workspace/application.jar

FROM eclipse-temurin:25-jre-noble AS runtime

RUN groupadd --system spring \
    && useradd --system --gid spring --home-dir /app --shell /usr/sbin/nologin spring

WORKDIR /app

COPY --from=build --chown=spring:spring /workspace/application.jar ./application.jar

USER spring:spring

ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
