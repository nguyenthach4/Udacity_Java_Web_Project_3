package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * PetMapper.
 *
 * @author NguyenT4.
 * @since 2023/10/27.
 */
@Component
public class PetMapper {
    private static ModelMapper modelMapper;

    private PetMapper() {

        modelMapper = new ModelMapper();
    }

    public PetDTO mapToDto(PetEntity petEntity) {
        PetDTO petDTO = modelMapper.map(petEntity, PetDTO.class);
        petDTO.setOwnerId(petEntity.getCustomer().getId());
        return petDTO;
    }

    public PetEntity mapToEntity(PetDTO petDTO) {

        return modelMapper.map(petDTO, PetEntity.class);
    }

    public List<PetDTO> mapToListDto(List<PetEntity> petEntityList) {
        List<PetDTO> petDTOList = new ArrayList<PetDTO>();
        for (PetEntity petEntity : petEntityList) {
            petDTOList.add(mapToDto(petEntity));
        }
        return petDTOList;
    }
}
