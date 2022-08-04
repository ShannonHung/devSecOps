FROM maven:3.6.3-adoptopenjdk-11 AS build
COPY . /tmp
WORKDIR /tmp
RUN mvn clean package -P prod

FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
RUN mkdir /opt/app
COPY --from=build /tmp/target/*.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]