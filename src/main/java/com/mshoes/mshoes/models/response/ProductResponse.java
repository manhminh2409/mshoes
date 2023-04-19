package com.mshoes.mshoes.models.response;

import java.util.List;

import com.mshoes.mshoes.models.dtos.ImageDTO;

import lombok.Data;

@Data
public class ProductResponse {

	private String name;

	private String description;

	private int visited;

	private String sku;

	private int price;

	private int discountPrice;

	private List<ImageDTO> images;

	private List<ColorResponse> colorResponses;
}
