package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.mapper.EmployeeMapper;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * EmployeeService.
 *
 * @author NguyenT4.
 * @since 2023/10/27.
 */
@Service
@Transactional
public class EmployeeService {
    public final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeDTO findById(long employeeId) {
        try {
            EmployeeEntity employee = this.employeeRepository.getOne(employeeId);
            if (Objects.isNull(employee)) {
                throw new ResourceNotFoundException("Employee", "employeeId", String.valueOf(employeeId));
            }
            return employeeMapper.mapToDto(employee);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public List<EmployeeDTO> findByService(LocalDate date, Set<EmployeeSkill> skills) {
        try {
            List<EmployeeEntity> employeeList = employeeRepository
                    .findAllByDaysAvailable(date.getDayOfWeek());
            if (CollectionUtils.isEmpty(employeeList)) {
                throw new ResourceNotFoundException("Employee", "dayOfWeek", String.valueOf(date.getDayOfWeek()));
            }
            List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
            for (EmployeeEntity employee : employeeList) {
                if (employee.getSkills().containsAll(skills)) {
                    employeeDTOList.add(employeeMapper.mapToDto(employee));
                }
            }
            return employeeDTOList;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        try {
            EmployeeEntity employee = employeeRepository.getOne(employeeId);
            if (Objects.isNull(employee)) {
                throw new ResourceNotFoundException("Employee", "employeeId", String.valueOf(employeeId));
            }
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public EmployeeDTO save(EmployeeEntity employee) {
        try {
            return employeeMapper.mapToDto(employeeRepository.save(employee));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageFormat
                    .format(CritterAppConstant.MessagePattem.SAVE_ERROR, CritterAppConstant.TableName.EMPLOYEE), ex);
        }

    }
}
