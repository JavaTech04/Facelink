FROM openjdk:22-jdk

COPY target/Facelink.jar Facelink.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "Facelink.jar"]