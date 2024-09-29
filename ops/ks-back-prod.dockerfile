FROM openjdk:21-slim AS build
RUN apt-get update && apt-get --assume-yes install wget

WORKDIR /ops
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
RUN tar -xvf apache-maven-3.9.9-bin.tar.gz
RUN mv apache-maven-3.9.9 /opt/
ENV M2_HOME='/opt/apache-maven-3.9.9'
ENV MAVEN_HOME='/opt/apache-maven-3.9.9'
ENV PATH="$PATH:/opt/apache-maven-3.9.9/bin"
RUN echo $PATH

WORKDIR /app
COPY ./ks-back/src ./src
COPY ./ks-back/pom.xml .
RUN mvn clean install

#ENTRYPOINT ["/bin/sh"]

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]
