FROM adoptopenjdk/openjdk11:alpine

COPY target/*.jar /opt/spring-boot/app.jar

EXPOSE 8080
CMD java -jar /opt/spring-boot/app.jar