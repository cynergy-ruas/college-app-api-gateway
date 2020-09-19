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
        return builder.routes()
            // route for message service
            .route(p -> p
                .path(messageServicePrefix)
                .filters(f -> f
                    .filter(authFilter.apply(new AuthGatewayFilterFactory.Config()))
                    .rewritePath(messageServicePrefix + "(?<segment>/?.*)", "${segment}"))
                .uri(messageServiceURI))

            // route for channel service
            .route(p -> p
                .path(channelServicePrefix)
                .filters(f -> f
                    .filter(authFilter.apply(new AuthGatewayFilterFactory.Config()))
                    .rewritePath(channelServicePrefix + "(?<segment>/?.*)", "${segment}"))
                .uri(channelServiceURI))

            // route for user service
            .route(p -> p
                .path(userServicePrefix)
                .filters(f -> f
                    .filter(authFilter.apply(new AuthGatewayFilterFactory.Config()))
                    .rewritePath(userServicePrefix + "(?<segment>/?.*)", "${segment}"))
                .uri(userServiceURI))

            // route for auth service
            .route(p -> p
                .path(authServicePrefix)
                .filters(f -> f
                    .rewritePath(authServicePrefix + "(?<segment>/?.*)", "${segment}"))
                .uri(authServiceURI))

            // building gateway routes
            .build();
    }
}
