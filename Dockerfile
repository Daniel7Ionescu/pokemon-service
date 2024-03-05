FROM openjdk:17-jdk-slim
COPY target/pokemon-service-0.0.1-SNAPSHOT.jar pokemon-service.jar
ENTRYPOINT ["java","-jar","pokemon-service.jar"]