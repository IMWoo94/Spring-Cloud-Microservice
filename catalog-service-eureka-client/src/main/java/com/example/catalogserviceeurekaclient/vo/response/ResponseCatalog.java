package com.example.catalogserviceeurekaclient.vo.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
	private String productId;
	private String productName;
	private Integer unitPrice;
	private Integer stock;
	private LocalDateTime createdAt;
}
