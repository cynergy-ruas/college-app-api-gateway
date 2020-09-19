package io.github.cynergy.apigateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.github.cynergy.apigateway.services.FirebaseService;

@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {
    FirebaseService service;

    public static class Config {}

    public AuthGatewayFilterFactory(FirebaseService service) {
        this.service = service;
    }

    @Override
    @SuppressWarnings("unused")
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.print("yayaya");

            if (true) {
                return chain.filter(exchange);
            }
            else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
}
