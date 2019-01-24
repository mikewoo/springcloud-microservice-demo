FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} microservice-eureka-server.jar
ENTRYPOINT ["java","-jar","/microservice-eureka-server.jar"]