FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Create a lightweight image with the JAR file
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/app.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]