#!/usr/bin/env bash
mvn clean install -DskipTests
docker build -t phone-number-validator .
docker run -p 8080:8080 phone-number-validator
