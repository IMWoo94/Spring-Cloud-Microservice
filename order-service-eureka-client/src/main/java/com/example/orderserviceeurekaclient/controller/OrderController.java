package com.example.orderserviceeurekaclient.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderserviceeurekaclient.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final Environment env;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s",
			env.getProperty("local.server.port"));
	}

}
