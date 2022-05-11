FROM hirokifujinuma/open-jdk-11
EXPOSE 8080
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

RUN apt -y update && apt -y install clamav clamav-daemon \
    && apt -y install curl \
    && curl -o /var/lib/clamav/main.cvd https://database.clamav.net/main.cvd \
    && curl -o /var/lib/clamav/daily.cvd https://database.clamav.net/daily.cvd \
    && curl -o /var/lib/clamav/bytecode.cvd https://database.clamav.net/bytecode.cvd \
    && chown -R clamav:clamav /var/lib/clamav

COPY boot.sh boot.sh
ENTRYPOINT ["sh","./boot.sh"]