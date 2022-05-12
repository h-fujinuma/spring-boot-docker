FROM clamav/clamav:0.104 as clamav

FROM hirokifujinuma/openjdk-11
EXPOSE 8080
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

COPY --from=clamav /var/lib/clamav /var/lib/clamav

COPY ./conf/clamd.conf /clamd.conf
COPY ./conf/freshclam.conf /freshclam.conf

ARG CLAMAV_VERSION=0.103.5+dfsg-0+deb11u1

RUN apt -y update && apt -y install clamav=$CLAMAV_VERSION \
    clamav-daemon=$CLAMAV_VERSION \
    && chown -R clamav:clamav /var/lib/clamav

COPY boot.sh boot.sh
ENTRYPOINT ["sh","./boot.sh"]