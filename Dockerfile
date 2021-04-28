# Starts from the Maven image
FROM maven:3.8.1-jdk-11 AS build

# Copy the Java source code inside the container
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app/src

# Compile the code
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11.0-jre-slim

EXPOSE 80 8080

ENV JAVA_OPTIONS="-Xmx512m -Xms256m"

RUN mkdir /app

COPY --from=build /home/app/target/*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
