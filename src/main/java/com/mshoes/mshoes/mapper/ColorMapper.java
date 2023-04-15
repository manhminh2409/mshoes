package com.mshoes.mshoes.mapper;

import com.mshoes.mshoes.models.Color;
import com.mshoes.mshoes.models.dtos.ColorDTO;
import com.mshoes.mshoes.models.requested.RequestedColor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ColorMapper {
    // mapper one model to dto
    ColorDTO mapModelToDTO(Color Color);

    // mapper list model to dto
    List<ColorDTO> mapModelToDTOs(List<Color> Colors);

    // mapper one dto to model
    Color mapDTOToModel(ColorDTO ColorDTO);

    // mapper list dto to model
    List<Color> mapDTOToModels(List<ColorDTO> ColorDTOS);

    @Mapping(target = "product.id", source = "productId")
    Color mapRequestedToModel(RequestedColor requestedColor);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateModel(@MappingTarget Color Color, RequestedColor requestedColor);

}
