FROM openjdk:8

ARG JAR_FILE
ADD ${JAR_FILE} app.jar
EXPOSE 90
ENTRYPOINT ["java", "-jar", "app.jar"]
