package com.example.orderserviceeurekaclient.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderserviceeurekaclient.dto.OrderDto;
import com.example.orderserviceeurekaclient.entity.OrderEntity;
import com.example.orderserviceeurekaclient.service.OrderService;
import com.example.orderserviceeurekaclient.vo.request.RequestOrder;
import com.example.orderserviceeurekaclient.vo.response.ResponseOrder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final Environment env;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in Order Service on PORT %s",
			env.getProperty("local.server.port"));
	}

	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(
		@RequestBody RequestOrder order,
		@PathVariable String userId
	) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderDto orderDto = mapper.map(order, OrderDto.class);
		orderDto.setUserId(userId);

		OrderDto createOrder = orderService.createOrder(orderDto);

		ResponseOrder responseOrder = mapper.map(createOrder, ResponseOrder.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(
		@PathVariable String userId
	) {
		Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<ResponseOrder> result = new ArrayList<>();
		orderList.forEach(order -> result.add(mapper.map(order, ResponseOrder.class)));

		return ResponseEntity.ok(result);
	}

}
