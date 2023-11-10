package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ScheduleMapper.
 *
 * @author NguyenT4.
 * @since 2023/10/24.
 */
@Component
public class ScheduleMapper {

    private ModelMapper modelMapper;

    private ScheduleMapper() {

        modelMapper = new ModelMapper();
    }

    public ScheduleDTO mapToDto(ScheduleEntity scheduleEntity) {
        ScheduleDTO scheduleDTO = modelMapper.map(scheduleEntity, ScheduleDTO.class);
        scheduleDTO.setEmployeeIds(scheduleEntity.getEmployees().
                stream().map(EmployeeEntity::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(scheduleEntity.getPets().stream()
                .map(PetEntity::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }

    public ScheduleEntity mapToEntity(ScheduleDTO scheduleDTO) {
        return modelMapper.map(scheduleDTO, ScheduleEntity.class);
    }

    public List<ScheduleDTO> mapToListDto(List<ScheduleEntity> scheduleEntityList) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
        for (ScheduleEntity scheduleEntity : scheduleEntityList) {
            scheduleDTOList.add(mapToDto(scheduleEntity));
        }
        return scheduleDTOList;
    }
}



