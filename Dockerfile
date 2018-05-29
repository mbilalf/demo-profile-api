FROM openjdk:8
ADD target/profile-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 90
ENTRYPOINT ["java", "-jar", "app.jar"]
