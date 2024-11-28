# Twitter API

## 📌 Description
This web application is a simple Twitter API 
which provides bunch of endpoints to simulate interaction with Twitter social media.

## 📌 Technologies
* Groovy
* Gradle
* Spring Boot
* MongoDB
* Spock Framework
* MapStruct
* Docker, Docker Compose

## 📌 Tests-Coverage
Service layer has 100% test coverage using Spock Framework

## 🚀 Quickstart
1. Fork this repository
2. Open project in your favorite IDE
3. Set up needed properties in `application.properties` file in `src/main/resources` directory
``` groovy
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.username=rootuser
spring.data.mongodb.password=rootpass
spring.data.mongodb.database=proxyseller
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
spring.data.mongodb.auto-index-creation=true
```
4. Run `./gradlew build` to build the project and generate mappers by MapStruct
5. Run `docker-compose up` to start MongoDB and other services
6. Run `./gradlew bootRun` to start the application
7. Open [Swagger](http://localhost:8080/swagger-ui/index.html) to see the API documentation
8. Enjoy it!


