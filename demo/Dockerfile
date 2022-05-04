FROM openjdk:11
USER root
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY boot.sh boot.sh
ENTRYPOINT ["sh","./boot.sh"]