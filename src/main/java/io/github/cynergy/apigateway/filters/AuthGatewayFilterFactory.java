package io.github.cynergy.apigateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {
    public static class Config {
        String secretKey;

        public Config(String secretKey) {
            this.secretKey = secretKey;
        }
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
