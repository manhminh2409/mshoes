package com.mshoes.mshoes.mapper;

import com.mshoes.mshoes.models.Color;
import com.mshoes.mshoes.models.Size;
import com.mshoes.mshoes.models.dtos.ProductDTO;
import com.mshoes.mshoes.models.requested.ProductRequest;
import com.mshoes.mshoes.models.Image;
import com.mshoes.mshoes.models.Product;
import com.mshoes.mshoes.models.response.ColorResponse;
import com.mshoes.mshoes.models.response.ProductItemResponse;
import com.mshoes.mshoes.models.response.ProductResponse;
import com.mshoes.mshoes.models.response.SizeResponse;
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

	//map danh sách ProductResponse với danh sách Product
	@Mapping(target = "colorResponses", source = "colors")
	ProductResponse toProductResponse(Product product);
	@Mapping(target = "sizeResponses", source = "sizes")
	ColorResponse mapColorToColorResponse(Color color);
	SizeResponse mapSizeToSizeResponse(Size size);


	List<ProductResponse> mapModelsToResponses(List<Product> products);


	//map product to productItemResponse
	ProductItemResponse mapModelToProductItemResponse(Product product);
	@Mapping(target = "category.id", source = "categoryId")
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapUrlsToImages")
	Product mapRequestedToModel(ProductRequest productRequest);

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
	void updateModel(@MappingTarget Product product, ProductRequest productRequest);
}
