package com.mshoes.mshoes.models.dtos;

import com.mshoes.mshoes.models.*;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

	private long id;

	private String name;

	private String description;

	private int visited;

	private String createdDate;

	private String modifiedDate;

	private int status;

	private String categoryTitle;

	private String authorFullname;

	private List<ImageDTO> images;

	private List<ProductOption> productOptions;

	private List<ProductVariant> productVariants;

}
