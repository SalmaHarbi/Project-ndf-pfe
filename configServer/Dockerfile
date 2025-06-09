FROM openjdk:17
WORKDIR /app
COPY target/configServer-0.0.1-SNAPSHOT.jar configserver.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "configserver.jar"]