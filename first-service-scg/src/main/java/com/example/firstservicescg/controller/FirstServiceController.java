package com.example.firstservicescg.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/first-service")
@Slf4j
@RequiredArgsConstructor
public class FirstServiceController {

	private final Environment env;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to the First Service. feat. Spring Cloud GateWay";
	}

	@GetMapping("/message")
	public String message(
		@RequestHeader("first-request") String header
	) {
		log.info("header = {}", header);
		return "Hello to the First Service. feat. Spring Cloud GateWay";
	}

	@GetMapping("/check")
	public String check(HttpServletRequest request) {
		log.info("First Service Check");
		log.info("server port ={}", env.getProperty("local.server.port"));
		log.info("request port ={}", request.getServerPort());
		return "Hi, there. This is a message from First Service.";
	}
}
