FROM openjdk:8-jdk-alpine
COPY ./target/*.jar app.jar
EXPOSE 9082
ENTRYPOINT ["java","-jar","/app.jar"]