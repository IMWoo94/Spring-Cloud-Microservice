package com.example.orderserviceeurekaclient.vo.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalPrice;
	private LocalDateTime createdAt;
	private String orderId;
}
