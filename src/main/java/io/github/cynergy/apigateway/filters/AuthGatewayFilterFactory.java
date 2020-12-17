package io.github.cynergy.apigateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import io.github.cynergy.apigateway.services.FirebaseService;
import io.github.cynergy.apigateway.services.VerificationResult;
import io.github.cynergy.apigateway.utils.ApiError;
import reactor.core.publisher.Mono;

@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {
    @Value("${headers.userid}")
    String userIdHeader;

    FirebaseService service;
    ObjectMapper objectMapper;

    public static class Config {
    }

    public AuthGatewayFilterFactory(FirebaseService service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // verifying token
            VerificationResult result = service
                    .verifyIdToken(exchange.getRequest().getHeaders().getFirst("Authorization"));

            if (result.isValid()) {
                // adding the headers
                ServerHttpRequest modifiedRequest = addRequestHeader(exchange.getRequest(), userIdHeader,
                        (String) result.getClaims().getOrDefault("user_id", "null"));

                // passing the modified request to the next filter
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } else {
                // creating buffer object to store the JSON reponse
                DataBuffer buffer = null;
                try {

                    // converting JSON reponse to bytes and putting it in the buffer
                    buffer = exchange.getResponse().bufferFactory()
                            .wrap(objectMapper.writeValueAsBytes(ApiError.unauthorizedError()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                // setting the status code for the response
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                // setting the content type of the response
                exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                
                // sending the reponse back to client, without routing the request to the service
                return exchange.getResponse().writeWith(Mono.just(buffer));
            }
        };
    }

    /**
     * Adds the headers to the request.
     * @param request
     * @param name
     * @param value
     * @return the modified request.
     */
    private ServerHttpRequest addRequestHeader(ServerHttpRequest request, String name, String value) {
        return request.mutate().header(name, value).build();
    }
}
