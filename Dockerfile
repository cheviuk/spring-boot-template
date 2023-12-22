FROM openjdk:21
COPY target/SpringBootTemplate.jar SpringBootTemplate.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "SpringBootTemplate.jar"]
HEALTHCHECK --interval=20s --retries=5 --timeout=5s --start-period=40s CMD curl --fail --silent localhost:8080/healthcheck | grep '\"status\":\"UP\"' || exit 1