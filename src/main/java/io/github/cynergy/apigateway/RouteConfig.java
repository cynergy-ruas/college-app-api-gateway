package io.github.cynergy.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cynergy.apigateway.filters.AuthGatewayFilterFactory;

@Configuration
public class RouteConfig {

    @Value("${messageservice.uri}")
    String messageServiceURI;

    @Value("${messageservice.prefix}")
    String messageServicePrefix;

    @Value("${messageservice.websocket.uri}")
    String messageServiceWebSocketURI;

    @Value("${messageservice.websocket.prefix}")
    String messageServiceWebSocketPrefix;

    @Value("${channelservice.uri}")
    String channelServiceURI;

    @Value("${channelservice.prefix}")
    String channelServicePrefix;

    @Value("${userservice.uri}")
    String userServiceURI;

    @Value("${userservice.prefix}")
    String userServicePrefix;

    @Value("${authservice.uri}")
    String authServiceURI;

    @Value("${authservice.prefix}")
    String authServicePrefix;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthGatewayFilterFactory authFilter) {
        System.out.println(messageServiceWebSocketPrefix);
        return builder.routes()
            // route for message service websocket connections
            .route(p -> p
                .path(messageServiceWebSocketPrefix)
                .uri(messageServiceWebSocketURI))

            // route for message service http requests
            .route(p -> p
                .path(messageServicePrefix) // match the path with the message service prefix
                .filters(f -> f
                    .filter(authFilter.apply(new AuthGatewayFilterFactory.Config()))) // validate the token
                .uri(messageServiceURI)) // send the request to message service

            // route for channel service
            .route(p -> p
                .path(channelServicePrefix) // match the path with the channel service prefix
                .filters(f -> f
                    .filter(authFilter.apply(new AuthGatewayFilterFactory.Config()))) // validate the token
                .uri(channelServiceURI)) // send the request to channel service

            // route for user service
            .route(p -> p
                .path(userServicePrefix) // match the path with the user service prefix
                .filters(f -> f
                    .filter(authFilter.apply(new AuthGatewayFilterFactory.Config()))) // validate the token
                .uri(userServiceURI)) // send the request to user service

            // route for auth service
            .route(p -> p
                .path(authServicePrefix) // match the path with the auth service prefix
                .uri(authServiceURI)) // send the request to auth service

            // building gateway routes
            .build();
    }
}
