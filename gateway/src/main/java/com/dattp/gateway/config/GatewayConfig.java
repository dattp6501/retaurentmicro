package com.dattp.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dattp.gateway.filters.AuthenticationFilter;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("notifitationservice", r -> r.path("/api/notification/**")
                        // .filters(f -> f.filter(filter))
                        .uri("lb://notifitationservice"))

                .route("productservice", r -> r.path("/api/product/**")
                        // .filters(f -> f.filter(filter))
                        .uri("lb://productservice"))
                .build();
    }

}