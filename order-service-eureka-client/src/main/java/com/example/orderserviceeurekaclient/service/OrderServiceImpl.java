package com.example.orderserviceeurekaclient.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.example.orderserviceeurekaclient.dto.OrderDto;
import com.example.orderserviceeurekaclient.entity.OrderEntity;
import com.example.orderserviceeurekaclient.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getQuantity() * orderDto.getUnitPrice());

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);

		orderRepository.save(orderEntity);

		OrderDto returnOrder = modelMapper.map(orderEntity, OrderDto.class);
		return returnOrder;
	}

	@Override
	public OrderDto getOrderByOderId(String orderId) {
		OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		OrderDto returnOrder = modelMapper.map(orderEntity, OrderDto.class);
		return returnOrder;
	}

	@Override
	public Iterable<OrderEntity> getOrdersByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}
}
