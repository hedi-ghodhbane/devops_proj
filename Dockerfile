FROM maven:3.8.4-jdk-11-slim as builder

RUN mkdir -p /home/project

COPY . /home/project

WORKDIR /home/project
# Compile and package the application to an executable JAR

RUN mvn install -Dmaven.test.skip=true

# For Java 11,

FROM adoptopenjdk/openjdk11:alpine-jre as runtime

COPY --from=builder /home/project/target/*.jar /home/project/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/project/app.jar"]
