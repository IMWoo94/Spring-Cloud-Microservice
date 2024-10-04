package com.example.userserviceeurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userserviceeurekaclient.vo.Greeting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final Environment env;

	@GetMapping("/health_check")
	public String status() {
		return "It's Working in User Service";
	}

	@GetMapping("/welcome")
	public String welcome() {
		return env.getProperty("greeting.message");
	}
}
