FROM openjdk:17
WORKDIR /app
COPY target/msexpense-0.0.1-SNAPSHOT.jar msexpense.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "msexpense.jar"]