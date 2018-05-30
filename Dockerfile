FROM openjdk:8

ENV SPRING_PROFILE=$SPRING_PROFILE

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

EXPOSE 90
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "app.jar"]
