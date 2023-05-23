FROM openjdk:17-jdk-alpine
COPY target/SpringBootTemplate.jar SpringBootTemplate.jar
ENTRYPOINT ["java", "-jar", "SpringBootTemplate.jar"]