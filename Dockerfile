FROM openjdk:11
WORKDIR /home/java
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","./app.jar"]
EXPOSE 8081