package com.mshoes.mshoes.mapper;

import com.mshoes.mshoes.models.DTO.RequestedUser;
import com.mshoes.mshoes.models.DTO.UserDTO;
import com.mshoes.mshoes.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(uses = ProductMapper.class)
public interface UserMapper {
	// mapper one model to dto
	UserDTO mapModelToDTO(User user);

	// mapper list model to dto
	List<UserDTO> mapModelToDTOs(List<User> users);

	// mapper one dto to model
	User mapDTOToModel(UserDTO userDTO);

	// mapper list dto to model
	List<User> mapDTOToModels(List<UserDTO> userDTOS);


	User mapRequestedToModel(RequestedUser requestedUser);
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateModel(@MappingTarget User user, RequestedUser requestedUser);

}
