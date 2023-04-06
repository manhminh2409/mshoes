package com.mshoes.mshoes.models.dtos;

import lombok.Data;

@Data
public class RequestedImage {
	private String url;

	private long productId;
}
