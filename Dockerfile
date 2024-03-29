FROM eclipse-temurin:21-jdk
WORKDIR /opt/raven
ENV SERVER_PORT 8080
COPY target/raven-server-0.1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-Dspring.profiles.active=demo", "-jar", "raven-server-0.1.0-SNAPSHOT.jar"]
EXPOSE $SERVER_PORT
