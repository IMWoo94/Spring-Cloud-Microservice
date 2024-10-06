package com.example.catalogserviceeurekaclient.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CatalogDto implements Serializable {
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalPrice;

	private String orderId;
	private String userId;
}
