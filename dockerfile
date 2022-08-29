FROM openjdk:11-jre-slim

WORKDIR /app
COPY . /app

ENTRYPOINT ["/app/gradlew", "run"]
