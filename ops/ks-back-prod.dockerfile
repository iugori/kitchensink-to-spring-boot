FROM openjdk:21-slim AS build
RUN apt-get update && apt-get --assume-yes install wget

WORKDIR /ops
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
RUN tar -xvf apache-maven-3.9.9-bin.tar.gz
RUN mv apache-maven-3.9.9 /opt/
ENV M2_HOME='/opt/apache-maven-3.9.9'
ENV MAVEN_HOME='/opt/apache-maven-3.9.9'

WORKDIR /app
COPY ./ks-back/.mvn ./.mvn
COPY ./ks-back/mvnw .
COPY ./ks-back/pom.xml .
RUN ./mvnw initialize

COPY ./ks-back/src ./src
RUN ./mvnw clean install

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]

# ENTRYPOINT ["/bin/sh"]