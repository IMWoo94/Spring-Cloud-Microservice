package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalGatewayFilterFactory extends AbstractGatewayFilterFactory<GlobalGatewayFilterFactory.Config> {
	public GlobalGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		// Global Pre Filter

		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			var id = request.getId();
			log.info("[{}] Global PRE filter baseMessage : {} request id -> {} ", Thread.currentThread().getName(),
				config.getBaseMessage(), id);

			if (config.isPreLogger()) {
				log.info("[{}] Global Filter Start : request id -> {}", Thread.currentThread().getName(), id);
			}
			// Global Post Filter
			return chain.filter(exchange)
				.then(Mono.fromRunnable(() -> {
					if (config.isPreLogger()) {
						log.info("[{}] Global POST filter : response code -> {}, id -> {}",
							Thread.currentThread().getName(), response.getStatusCode(), id);
					}
				}));
		});
	}

	@Data
	public static class Config {
		// Put the configuration properties
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;

	}

}
