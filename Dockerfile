FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]