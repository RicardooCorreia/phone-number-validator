# Phone Number Validator

## Description

This is a Phone Number Validator application.
The international phone types are provided from a csv file that contains the country, the indicative and the regex for the number.
The application also provides support for listing, filtering and save customers.

### Backend

The backend layer is developed in Java with Spring. It uses Hibernate JPA for database access, Mockito and Hamcrest for testing, Maven for building and dependency management and Checkstyle (code cleanness) and Jacoco (Code coverage) for clean code practices.

### Frontend

Unfortunately, I was unable to get String MVC and Thymeleaf to work, so the alternative that I found was to make a index.html page and add an Javascript file to manage the page. 

## Execution

This application can be run by a jar or a by a Docker file

### Build

`mvn clean install`

### Fat jar

`java -jar target/phone-number-validator.jar`

**OR**

`sh scripts/runAppByJar.sh`

### Docker

`docker build -t phone-number-validator .`

`docker run -p 8080:8080 phone-number-validator`

**OR**

`sh scripts/runByDocker.sh`

*Note*: For some reason the database in the docker container is marked as read-only, needs further investigation.