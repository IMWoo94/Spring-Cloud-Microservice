package com.example.orderserviceeurekaclient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orderserviceeurekaclient.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	OrderEntity findByOrderId(String orderId);

	List<OrderEntity> findByUserId(String userId);
}
