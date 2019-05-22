# note-svc
Note management service, exposes storage, retrieval and management of notes.

## Table of contents

- [requirements](#requirements)
- [endpoints](#endpoints)
- [quick start](#quick-start)


## requirements
- mvn 3.6.0
- java 8
- mongodb
- docker

## endpoints

This project uses [Swagger](https://swagger.io/) for API documentation. To see the the documentation, start the service (see 
[quick start](#quick-start)) and visit the following url: 
```
http://localhost:9090/note-svc/swagger-ui.html#/
```

## quick start

#####Start service(requires mongo already running)
```
mvn clean install
mvn spring-boot:run
```

#####Skip all tests
```
mvn clean verify -DskipTests=true
```

#####Skip integration tests
```
mvn clean verify -DskipITs=true
```

#####Skip mutation tests
```
mvn clean verify -DskipPITs=true
```

#####Set environment
```
mvn spring-boot:run -Dspring.profiles.active=local
```

