package com.mshoes.mshoes.models.DTO;

import lombok.Data;

import java.util.List;

@Data
public class RequestedProduct {
	private String name;

	private String description;

	private List<String> imageUrls;

	private long categoryId;

	private long userId;
}
