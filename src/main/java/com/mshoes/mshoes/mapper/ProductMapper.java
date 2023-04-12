package com.mshoes.mshoes.mapper;

import com.mshoes.mshoes.models.dtos.ProductDTO;
import com.mshoes.mshoes.models.requested.RequestedProduct;
import com.mshoes.mshoes.models.Image;
import com.mshoes.mshoes.models.Product;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ProductMapper {
	// mapper one model to dto
	@Mapping(target = "categoryTitle", source = "category.title")
	@Mapping(target = "authorFullname", source = "user.fullname")
	ProductDTO mapModelToDTO(Product product);

	// mapper list model to dto
	@Mapping(target = "categoryTitle", source = "category.title")
	@Mapping(target = "authorFullname", source = "user.fullname")
	List<ProductDTO> mapModelToDTOs(List<Product> products);

	// mapper one dto to model
	Product mapDTOToModel(ProductDTO productDTO);

	// mapper list dto to model
	List<Product> mapDTOToModels(List<ProductDTO> productDTOS);

	@Mapping(target = "category.id", source = "categoryId")
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapUrlsToImages")
	Product mapRequestedToModel(RequestedProduct requestedProduct);

	@Named("mapUrlsToImages")
	default List<Image> mapUrlsToImages(List<String> imageUrls) {
		if (imageUrls == null) {
			return new ArrayList<>();
		}
		return imageUrls.stream().map(Image::new).collect(Collectors.toList());
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "category.id", source = "categoryId")
	@Mapping(target = "user.id", source = "userId")
	void updateModel(@MappingTarget Product product, RequestedProduct requestedProduct);
}
