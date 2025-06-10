FROM openjdk:17
WORKDIR /app
COPY target/msuser-0.0.1-SNAPSHOT.jar msuser.jar
COPY wait-for.sh wait-for.sh
RUN chmod +x wait-for.sh
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "msuser.jar"]
ENTRYPOINT ["./wait-for.sh", "configserver:9999", "--", "java", "-jar", "msuser.jar"]