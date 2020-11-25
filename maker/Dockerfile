FROM openjdk:14-jdk

ARG JAR_FILE=target/make-promises-maker-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} maker-app.jar

ENTRYPOINT ["java","-jar","/maker-app.jar"]