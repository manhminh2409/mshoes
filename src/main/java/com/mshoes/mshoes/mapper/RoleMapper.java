package com.mshoes.mshoes.mapper;

import com.mshoes.mshoes.models.DTO.RoleDTO;
import com.mshoes.mshoes.models.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
	RoleDTO mapModelToDTO(Role role);

	// mapper list model to dto
	List<RoleDTO> mapModelToDTOs(List<Role> roles);

	// mapper one dto to model
	Role mapDTOToModel(RoleDTO roleDTO);

	// mapper list dto to model
	List<Role> mapDTOToModels(List<RoleDTO> roleDTOS);
}
