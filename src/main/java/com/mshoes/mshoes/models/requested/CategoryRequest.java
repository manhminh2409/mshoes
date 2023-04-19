package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class CategoryRequest {
	private String title;

	private String description;

	private String createdDate;

	private String modifiedDate;

	private int status;
}
