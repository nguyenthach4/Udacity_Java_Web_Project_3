package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CustomerMapper.
 *
 * @author NguyenT4.
 * @since 2023/10/27.
 */
@Component
public class CustomerMapper {

    private ModelMapper modelMapper;

    private CustomerMapper() {
        modelMapper = new ModelMapper();
    }

    public CustomerDTO mapToDto(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = modelMapper.map(customerEntity, CustomerDTO.class);
        List<Long> petIds = customerEntity.getPets().stream().map(PetEntity::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public CustomerEntity mapToEntity(CustomerDTO customerDTO) {

        return modelMapper.map(customerDTO, CustomerEntity.class);
    }

    public List<CustomerDTO> mapToListDto(List<CustomerEntity> customerEntityList) {
        List<CustomerDTO> employeeDTOList = new ArrayList<CustomerDTO>();
        for (CustomerEntity customerEntity : customerEntityList) {
            employeeDTOList.add(mapToDto(customerEntity));
        }
        return employeeDTOList;
    }
}
