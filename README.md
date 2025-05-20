# Project Overview
Basic MVC architecture. Controller -> Service -> Repository

# Prerequisites
Java 17

# Setup Instructions
1. clone the project
2. run `./gradlew build` to build the project (download dependencies)
3. run `./gradlew bootRun` to start springboot

# API Documentation
todo: use swagger

# Testing Instructions
run `./gradlew test` to run both integration test & unit test

# Linting Instructions
run `./gradlew checkstyleTest`

# Assumption:
1. no pharmacy & patient & pharmacy drug contract relasionship edit API, use SQL to insert fix record
2. no updates for existing prescription
