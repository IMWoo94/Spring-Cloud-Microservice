package com.example.apigatewayservice.filter;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderGatewayFilterFactory
	extends AbstractGatewayFilterFactory<AuthorizationHeaderGatewayFilterFactory.Config> {
	private final Environment environment;

	public AuthorizationHeaderGatewayFilterFactory(Environment environment) {
		super(AuthorizationHeaderGatewayFilterFactory.Config.class);
		this.environment = environment;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			String jwt = authorizationHeader.replace("Bearer ", "");

			if (!isJwtValid(jwt)) {
				return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		});
	}

	private boolean isJwtValid(String jwt) {
		String key = environment.getProperty("token.secret");
		// 키는 최소 256비트 (32바이트) 이상의 길이를 가져야 합니다.
		SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
		boolean returnValue = true;

		String subject = null;
		try {
			subject = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(jwt)
				.getPayload()
				.getSubject();
		} catch (Exception e) {
			returnValue = false;
		}

		if (subject == null || subject.isEmpty()) {
			returnValue = false;
		}

		return returnValue;
	}

	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);

		log.error(message);
		return response.setComplete();
	}

	public static class Config {

	}
}