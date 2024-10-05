package com.example.userserviceeurekaclient.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 모든 요청에 대해서 인증 처리를 진행 할 것이다.
		http.authorizeHttpRequests((request) ->
				request
					.requestMatchers("/users/**", "/health_check", "/welcome")
					.permitAll()
					.requestMatchers(PathRequest.toH2Console())
					.permitAll()
			)
			// h2 웹 콘솔 iframe 화면 구성 허용
			// default : HeaderWriterFilter -> X-Frame-Options DENY
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
			.cors(Customizer.withDefaults())
			// REST API 를 개발함으로 Session 관련 생성되지 않도록 처리
			.sessionManagement(
				(session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// csrf 검증은 제외
			.csrf(CsrfConfigurer::disable)
			// 기본 인증 방식을 사용
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	@ConditionalOnProperty(name = "spring.h2.console.enable", havingValue = "true")
	// h2 console spring security 통과 하지 않도록 무시
	public WebSecurityCustomizer webSecurityCustomizer() {
		return webSecurity ->
			webSecurity
				.ignoring()
				.requestMatchers(PathRequest.toH2Console());
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
