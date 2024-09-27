package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalExtendsGatewayFilterFactory implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// pre
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		var id = request.getId();

		log.info("[{}] GlobalExtends PRE filter request id -> {} ", Thread.currentThread().getName(), id);

		// post
		return chain.filter(exchange)
			.then(Mono.fromRunnable(() -> {
				log.info("[{}] GlobalExtends POST filter : response code -> {}, id -> {}",
					Thread.currentThread().getName(), response.getStatusCode(), id);
			}));
	}

	@Override
	public int getOrder() {
		return -1;
	}
}
