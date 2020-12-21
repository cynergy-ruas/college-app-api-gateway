# API Gateway

This repo contains the code for the API gateway required for the backend of the college app.

## Development

First, set the `GOOGLE_APPLICATION_CREDENTIALS` environment variable to point to the location of the service accounts json file. (contact repo admins for the file).
This project, for now, uses firebase to validate the tokens created by the authentication microservice.

Make sure you have jdk 11 (preferably adopt openjdk 11) installed. Run the gateway using

```
$ GOOGLE_APPLICATION_CREDENTIALS=service-account.json \
  MESSAGE_SERVICE_URI=<uri of message service> \
  USER_SERVICE_URI=<uri of user service> \
  CHANNEL_SERVICE_URI=<uri of channel service> \
  AUTH_SERVICE_URI=<uri of auth service> \
  ./gradlew bootRun
```

fill the `application.properties` file with the URIs of the other microservices.

## Production

Create the docker image by running
```
./gradlew bootBuildImage --imageName=cynergyruas/ruas-app:api-gateway-0.0.1
```

Make sure you have the `service-account.json` file in the current directory. To create and start a container, run the following command:
```
docker run -p 8080:8080 -v $(pwd)/service-account.json:/workspace/service-account.json \
    -e GOOGLE_APPLICATION_CREDENTIALS=/workspace/service-account.json \
    -e MESSAGE_SERVICE_URI=<uri of message service> \
    -e USER_SERVICE_URI=<uri of user service> \
    -e CHANNEL_SERVICE_URI=<uri of channel service> \
    -e AUTH_SERVICE_URI=<uri of auth service> \
    cynergyruas/ruas-app:api-gateway-0.0.1
```

The image can be run by
```
$ docker run -p 8080:8080 apigateway:0.0.1
```

## Future improvements

1. Use Eureka for service discovery