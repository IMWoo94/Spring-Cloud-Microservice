package com.example.orderserviceeurekaclient.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestOrder {
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
}
