package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ScheduleRepository.
 *
 * @author NguyenT4.
 * @since 2023/10/24.
 */
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> getAllByPets(PetEntity pet);

    List<ScheduleEntity> getAllByEmployees(EmployeeEntity employee);

    List<ScheduleEntity> findAllByPetsIn(List<PetEntity> pets);
}
