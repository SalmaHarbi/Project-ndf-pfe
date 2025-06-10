FROM openjdk:17
WORKDIR /app
COPY target/msexpensereport-0.0.1-SNAPSHOT.jar msexpensereport.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "msexpensereport.jar"]
