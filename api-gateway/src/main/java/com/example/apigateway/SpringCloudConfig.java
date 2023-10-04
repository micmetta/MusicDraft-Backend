package com.example.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration

public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/authenticationService/**")
                        .uri("http://authentication-service:8081"))

                .route(r -> r.path("/api/v1/cartemazzi/**")
                        .uri("http://carte-e-mazzi-service:9092"))

                .route(r -> r.path("/api/v1/homeService/**")
                        .uri("http://home-service:8082"))

                .route(r -> r.path("/api/v1/collect/**")
                        .uri("http://collectdata-service:9090"))

                .route(r -> r.path("/api/v1/homeService/scambiController/**")
                        .uri("http://home-service:8082"))

                .route(r -> r.path("/api/v1/marketplace/**")
                        .uri("http://marketplace-service:9091"))

                .route(r -> r.path("/api/v1/matchmakingService/matchmakingController/**")
                        .uri("http://matchmaking-service:8083"))

                .route(r -> r.path("/api/v1/matchmakingService/riepilogoPartitaConclusaController/**")
                        .uri("http://matchmaking-service:8083"))

                .build();
    }
}
