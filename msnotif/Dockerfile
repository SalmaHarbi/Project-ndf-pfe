FROM openjdk:17
WORKDIR /app
COPY target/msnotif-0.0.1-SNAPSHOT.jar msnotif.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "msnotif.jar"]