package com.example.orderserviceeurekaclient.service;

import com.example.orderserviceeurekaclient.dto.OrderDto;
import com.example.orderserviceeurekaclient.entity.OrderEntity;

public interface OrderService {
	OrderDto createOrder(OrderDto orderDto);

	OrderDto getOrderByOderId(String orderId);

	Iterable<OrderEntity> getOrdersByUserId(String userId);
}
