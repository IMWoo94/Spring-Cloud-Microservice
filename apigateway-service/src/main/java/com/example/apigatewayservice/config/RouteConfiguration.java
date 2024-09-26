package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

	@Bean
	public RouteLocator getewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(r -> r.path("/second-service/**")
				.uri("http://localhost:8082"))
			.build();
	}
}
