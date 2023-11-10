package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeMapper.
 *
 * @author NguyenT4.
 * @since 2023/10/27.
 */
@Component
public class EmployeeMapper {

    private ModelMapper modelMapper;

    private EmployeeMapper() {

        modelMapper = new ModelMapper();
    }

    public EmployeeDTO mapToDto(EmployeeEntity employeeEntity) {

        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public EmployeeEntity mapToEntity(EmployeeDTO employeeDTO) {

        return modelMapper.map(employeeDTO, EmployeeEntity.class);
    }

    public List<EmployeeDTO> mapToListDto(List<EmployeeEntity> employeeEntityList) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
        for (EmployeeDTO employeeEntity : employeeDTOList) {
            employeeDTOList.add(modelMapper.map(employeeEntity, EmployeeDTO.class));
        }
        return employeeDTOList;
    }
}
