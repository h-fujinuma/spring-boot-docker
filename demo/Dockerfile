FROM openjdk:11 AS jdk
USER root
EXPOSE 8080
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY boot.sh boot.sh
ENTRYPOINT ["sh","./boot.sh"]