# This file describes how to build and run the application container image.
# File purpose: contains container build/runtime configuration blocks.
FROM maven:3.9.9-eclipse-temurin-17 AS build
# Block: defines one container image build/runtime step.
WORKDIR /app
# Block: defines one container image build/runtime step.
COPY . .
# Block: defines one container image build/runtime step.
RUN mvn -q -DskipTests package

# Block: defines one container image build/runtime step.
FROM eclipse-temurin:17-jre
# Block: defines one container image build/runtime step.
WORKDIR /app
# Block: defines one container image build/runtime step.
COPY --from=build /app/target/book-store-service-0.0.1-SNAPSHOT.jar app.jar
# Block: defines one container image build/runtime step.
EXPOSE 8084
# Block: defines one container image build/runtime step.
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]

