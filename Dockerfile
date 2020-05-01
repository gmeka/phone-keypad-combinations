FROM openjdk:11-jre

WORKDIR /opt/docker

COPY target/phone-keypad-combinations.jar .

ENTRYPOINT ["java", "-jar", "./phone-keypad-combinations.jar"]

EXPOSE 8080
