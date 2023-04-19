package com.mshoes.mshoes.models.requested;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
	private String name;

	private String description;

	private String sku;

	private int price;

	private int discountPrice;

	private List<String> imageUrls;

	private Long categoryId;

	private Long userId;

	private List<ColorRequest> colors;
}
