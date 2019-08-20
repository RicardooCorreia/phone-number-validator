#!/usr/bin/env bash
mvn clean install -DskipTests
java -jar target/phone-number-validator.jar
