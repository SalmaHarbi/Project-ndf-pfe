FROM openjdk:17
WORKDIR /app
COPY target/gateway-0.0.1-SNAPSHOT.jar gateway.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "gateway.jar"]


