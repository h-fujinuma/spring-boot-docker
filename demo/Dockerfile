FROM clamav/clamav:0.104 AS clamav
USER root

FROM openjdk:11 AS jdk
USER root
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY --from=clamav /etc/clamav /etc/clamav
COPY --from=clamav /usr/lib/libclamunrar_iface.so.9.1.0 /usr/lib/libclamunrar_iface.so.9.1.0
COPY --from=clamav /usr/lib/libclamav.so.9 /usr/lib/libclamav.so.9
COPY --from=clamav /usr/lib/libclamunrar.so.9.1.0 /usr/lib/libclamunrar.so.9.1.0
COPY --from=clamav /usr/lib/libclamunrar_iface.so.9 /usr/lib/libclamunrar_iface.so.9
COPY --from=clamav /usr/lib/libclamav.so.9.1.0 /usr/lib/libclamav.so.9.1.0
COPY --from=clamav /usr/lib/libclamav.so /usr/lib/libclamav.so
COPY --from=clamav /usr/lib/libclamunrar.so /usr/lib/libclamunrar.so
COPY --from=clamav /usr/lib/libclamunrar_iface.so /usr/lib/libclamunrar_iface.so
COPY --from=clamav /usr/lib/libclamunrar.so.9 /usr/lib/libclamunrar.so.9
COPY --from=clamav /usr/lib/libfreshclam.so.2.0.2 /usr/lib/libfreshclam.so.2.0.2
COPY --from=clamav /usr/lib/libfreshclam.so /usr/lib/libfreshclam.so
COPY --from=clamav /usr/lib/libclammspack.so.0.8.0 /usr/lib/libclammspack.so.0.8.0
COPY --from=clamav /usr/lib/libclammspack.so /usr/lib/libclammspack.so
COPY --from=clamav /usr/lib/libclammspack.so.0 /usr/lib/libclammspack.so.0
COPY --from=clamav /usr/lib/libfreshclam.so.2 /usr/lib/libfreshclam.so.2
COPY --from=clamav /usr/sbin/clamd /usr/sbin/clamd
COPY --from=clamav /usr/bin/clamav-config /usr/bin/clamav-config
COPY --from=clamav /usr/bin/clamconf /usr/bin/clamconf
COPY --from=clamav /usr/bin/clamdtop /usr/bin/clamdtop
COPY --from=clamav /usr/bin/clamsubmit /usr/bin/clamsubmit
COPY --from=clamav /usr/bin/clamdscan /usr/bin/clamdscan
COPY --from=clamav /usr/bin/freshclam /usr/bin/freshclam
COPY --from=clamav /usr/bin/clambc /usr/bin/clambc
COPY --from=clamav /usr/local/bin/clamdcheck.sh /usr/local/bin/clamdcheck.sh
COPY --from=clamav /var/lib/clamav /var/lib/clamav
COPY --from=clamav /var/log/clamav /var/log/clamav
COPY --from=clamav /lib/libc.musl-x86_64.so.1 /lib/libc.musl-x86_64.so.1
COPY --from=clamav /lib/ld-musl-x86_64.so.1 /lib/ld-musl-x86_64.so.1
COPY ${JAR_FILE} app.jar
COPY boot.sh boot.sh
ENTRYPOINT ["sh","./boot.sh"]