package com.example.firstservicescg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {

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
	public String check() {
		log.info("First Service Check");
		return "Hi, there. This is a message from First Service.";
	}
}
