FROM openjdk:17
WORKDIR /app
COPY target/msuser-0.0.1-SNAPSHOT.jar msuser.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "msuser.jar"]