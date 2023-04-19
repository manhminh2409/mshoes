package com.mshoes.mshoes.models.response;

import lombok.Data;

@Data
public class OrderItemResponse {
	private int quantity;

	private ProductItemResponse productItemResponse;

	private String size;

	private String color;
}
