# note-svc
Note management service, exposes storage, retrieval and management of notes.

## Table of Contents

- [requirements](#requirements)
- [endpoints](#endpoints)
- [quick start](#quick-start)


## Requirements
- mvn 3.6.0
- java 8
- mongodb
- docker

## Endpoints

This project uses [Swagger](https://swagger.io/) for API documentation. To see the the documentation, start the service (see 
[quick start](#quick-start)) and visit the following url: 
```
http://localhost:9090/note-svc/swagger-ui.html#/
```

## Quick Start

### Start Service with Dependencies
```
mvn clean install
docker-compose up -d
```

### Start Service Locally(requires mongo already running)
```
mvn clean install
mvn spring-boot:run
```

### Select Environment
```
mvn spring-boot:run -Dspring.profiles.active=local
```

### Skip All Tests
```
mvn clean verify -DskipTests=true
```

### Skip Integration Tests
```
mvn clean verify -DskipITs=true
```

### Skip Mutation Tests
```
mvn clean verify -DskipPITs=true
```
