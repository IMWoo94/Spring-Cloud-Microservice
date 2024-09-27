package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {
	public CustomGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		// Custom Pre Filter

		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			var id = request.getId();
			log.info("[{}] Custom PRE filter : request id -> {}", Thread.currentThread().getName(), id);

			// Custom Post Filter
			return chain.filter(exchange)
				.then(Mono.fromRunnable(() -> {
					log.info("[{}] Custom POST filter : response code -> {}, id -> {}",
						Thread.currentThread().getName(), response.getStatusCode(), id);
				}));
		});
	}

	public static class Config {
		// Put the configuration properties
	}

}
