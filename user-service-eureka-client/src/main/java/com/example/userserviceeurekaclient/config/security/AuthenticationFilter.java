package com.example.userserviceeurekaclient.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.userserviceeurekaclient.dto.UserDto;
import com.example.userserviceeurekaclient.service.CustomUserDetailsService;
import com.example.userserviceeurekaclient.vo.request.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final CustomUserDetailsService customUserDetailsService;
	private final Environment environment;

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws
		AuthenticationException {
		try {
			RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.getEmail(),
					creds.getPassword(),
					new ArrayList<>()
				)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		log.info("authentication successful : {}", (User)authResult.getPrincipal());
		String username = ((User)authResult.getPrincipal()).getUsername();
		UserDto userDetails = customUserDetailsService.getCurrentUser(username);

		byte[] keyBytes = Decoders.BASE64.decode(environment.getProperty("token.secret"));
		// 키는 최소 256비트 (32바이트) 이상의 길이를 가져야 합니다.
		SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

		String token = Jwts.builder()
			.subject(userDetails.getUserId())
			.expiration(new Date(System.currentTimeMillis() +
				Long.parseLong(environment.getProperty("token.expiration_time"))))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}
}
