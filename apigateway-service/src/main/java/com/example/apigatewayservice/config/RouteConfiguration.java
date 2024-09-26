package com.example.apigatewayservice.config;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.*;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.*;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfiguration {

	@Bean
	public RouterFunction<ServerResponse> routes() {
		return route("second-service")
			.route(path("/second-service/**"), http("http://localhost:8082"))
			.build();
	}
}
