package com.mshoes.mshoes.mapper;

import com.mshoes.mshoes.models.DTO.ImageDTO;
import com.mshoes.mshoes.models.DTO.RequestedImage;
import com.mshoes.mshoes.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ImageMapper {

	// map one model to dto
	@Mapping(target = "url", source = "image.url")
	ImageDTO mapModelToDTO(Image image);

	// map list model to dto
	@Mapping(target = "url", source = "image.url")
	List<ImageDTO> mapModelToDTOs(List<Image> images);

	// mapper one dto to model
	Image mapDTOToModel(ImageDTO imageDTO);

	// map list dto to model
	List<Image> mapDTOToModels(List<ImageDTO> imageDTOS);

	@Mapping(target = "product.id", source = "productId")
	Image mapRequestedToModel(RequestedImage requestedImage);
}
