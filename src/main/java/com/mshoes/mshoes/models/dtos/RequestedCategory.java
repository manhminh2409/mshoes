package com.mshoes.mshoes.models.dtos;

import lombok.Data;

@Data
public class RequestedCategory {
	private String title;

	private String description;

	private String createdDate;

	private String modifiedDate;

	private int status;
}
