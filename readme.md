# API Gateway

This repo contains the code for the API gateway required for the backend of the college app.

## Development

First, set the `GOOGLE_APPLICATION_CREDENTIALS` environment variable to point to the location of the service accounts json file. (contact repo admins for the file).
This project, for now, uses firebase to validate the tokens created by the authentication microservice.

Make sure you have jdk 11 (preferably adopt openjdk 11) installed. Run the gateway using

```
$ ./gradlew bootRun
```

fill the `application.properties` file with the URIs of the other microservices.

## Future improvements

1. Use Eureka for service discovery