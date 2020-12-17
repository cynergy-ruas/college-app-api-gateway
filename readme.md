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

## Production

To make a docker image, first build the application by using
```
$ ./gradlew build
$ mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
```

then create the docker image by running
```
$ docker build -t apigateway:0.0.1 .
```

The image can be run by
```
$ docker run -p 8080:8080 apigateway:0.0.1
```

## Future improvements

1. Use Eureka for service discovery