FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/fattehpur.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prd"]
