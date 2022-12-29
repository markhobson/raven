FROM eclipse-temurin:17-jdk
WORKDIR /opt/raven
ENV SERVER_PORT 80
COPY target/raven-server-0.1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-Dspring.profiles.active=demo", "-jar", "raven-server-0.1.0-SNAPSHOT.jar"]
EXPOSE $SERVER_PORT
