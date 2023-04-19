package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class ImageRequest {
	private String url;

	private Long productId;
}
