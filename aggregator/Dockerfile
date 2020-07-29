FROM hirokimatsumoto/alpine-openjdk-11:latest as jlink-package
# First: generate java runtime module by jlink.

RUN jlink \
     --module-path /opt/java/jmods \
     --compress=2 \
     --add-modules jdk.jfr,jdk.management.agent,java.base,java.logging,java.xml,jdk.unsupported,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
     --no-header-files \
     --no-man-pages \
     --output /opt/jdk-11-mini-runtime

# Second: generate run image.
FROM alpine:3.8

ENV JAVA_HOME=/opt/jdk-11-mini-runtime
ENV PATH="$PATH:$JAVA_HOME/bin"

COPY --from=jlink-package /opt/jdk-11-mini-runtime /opt/jdk-11-mini-runtime
COPY target/*.jar /opt/spring-boot/app.jar

CMD java \
    -XX:StartFlightRecording=name=sample,filename=/spring-boot/jfr/sample.jfr,delay=30s,maxage=2h,maxsize=10m,dumponexit=true,settings=/spring-boot/config/profile.jfc \
    -jar /opt/spring-boot/app.jar