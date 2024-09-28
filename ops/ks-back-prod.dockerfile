FROM openjdk:21-slim
COPY ks-back/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]