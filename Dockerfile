FROM openjdk:8

WORKDIR /usr/src/phone-number-validator/

COPY target/phone-number-validator.jar .

CMD ["java", "-jar", "phone-number-validator.jar"]
