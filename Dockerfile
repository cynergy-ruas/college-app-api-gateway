FROM adoptopenjdk/openjdk11:alpine-slim

ARG SERVICE_FILE=service-account.json
ARG DEPENDENCY=build/dependency

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /opt/app

COPY ${SERVICE_FILE} service-account.json
COPY ${DEPENDENCY}/BOOT-INF/lib lib
COPY ${DEPENDENCY}/META-INF META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes app

ENV GOOGLE_APPLICATION_CREDENTIALS=service-account.json
ENTRYPOINT java -cp app:/opt/app/lib/* io.github.cynergy.apigateway.ApiGatewayApplication
