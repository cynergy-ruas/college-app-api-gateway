FROM adoptopenjdk/openjdk11:alpine-slim

ARG SERVICE_FILE=service-account.json
ARG DEPENDENCY=build/dependency

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY ${SERVICE_FILE} service-account.json
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENV GOOGLE_APPLICATION_CREDENTIALS=service-account.json
ENTRYPOINT java -cp app:app/lib/* io.github.cynergy.apigateway.ApiGatewayApplication
