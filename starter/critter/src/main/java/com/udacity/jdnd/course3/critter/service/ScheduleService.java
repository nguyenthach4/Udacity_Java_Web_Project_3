package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.*;

/**
 * ScheduleService.
 *
 * @author NguyenT4.
 * @since 2023/10/24.
 */
@Service
@Transactional
public class ScheduleService {
    public final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public List<ScheduleDTO> findAll() {
        try {
            if (CollectionUtils.isEmpty(scheduleRepository.findAll())) {
                return Collections.emptyList();
            }

            return scheduleMapper.mapToListDto(scheduleRepository.findAll());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public List<ScheduleDTO> findAllByPetId(long petId) {
        try {
            PetEntity pet = this.petRepository.getOne(petId);
            if (Objects.isNull(pet)) {
                throw new ResourceNotFoundException("Pet", "petId", String.valueOf(petId));
            }
            List<ScheduleEntity> scheduleList = this.scheduleRepository.getAllByPets(pet);
            if (CollectionUtils.isEmpty(scheduleList)) {
                    return Collections.emptyList();
            }
            return scheduleMapper.mapToListDto(scheduleList);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public List<ScheduleDTO> findAllByEmployeeId(long employeeId) {
        try {
            EmployeeEntity employee = this.employeeRepository.getOne(employeeId);
            if (Objects.isNull(employee)) {
                throw new ResourceNotFoundException("Employee", "employeeId", String.valueOf(employeeId));
            }
            List<ScheduleEntity> scheduleList = this.scheduleRepository.getAllByEmployees(employee);
            if (CollectionUtils.isEmpty(scheduleList)) {
                return Collections.emptyList();
            }
            return scheduleMapper.mapToListDto(scheduleList);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public List<ScheduleDTO> findAllByCustomerId(long customerId) {
        try {
            CustomerEntity customer = this.customerRepository.getOne(customerId);
            if (Objects.isNull(customer)) {
                throw new ResourceNotFoundException("Customer", "customerId", String.valueOf(customerId));
            }
            List<ScheduleEntity> scheduleList = this.scheduleRepository.findAllByPetsIn(new ArrayList<>(customer.getPets()));
            if (CollectionUtils.isEmpty(scheduleList)) {
                throw new ResourceNotFoundException("Schedule", "Pets", String.valueOf(customer.getPets()));
            }
            return scheduleMapper.mapToListDto(scheduleList);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public ScheduleDTO save(ScheduleEntity schedule, List<Long> employeeIds, List<Long> petIds) {
        try {
            List<EmployeeEntity> employees = employeeRepository.findAllById(employeeIds);
            if (CollectionUtils.isEmpty(employees)) {
                throw new ResourceNotFoundException("Employees", "employeeIds", String.valueOf(employeeIds));
            }
            List<PetEntity> pets = petRepository.findAllById(petIds);
            if (CollectionUtils.isEmpty(pets)) {
                throw new ResourceNotFoundException("Pet", "petIds", String.valueOf(petIds));
            }
            schedule.setEmployees(new HashSet<>(employees));
            schedule.setPets(new HashSet<>(pets));
            return scheduleMapper.mapToDto(scheduleRepository.save(schedule));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageFormat
                    .format(CritterAppConstant.MessagePattem.SAVE_ERROR, CritterAppConstant.TableName.SCHEDULE), ex);
        }
    }
}
