package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.mapper.CustomerMapper;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
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
 * CustomerService.
 *
 * @author NguyenT4.
 * @since 2023/10/27.
 */
@Service
@Transactional
public class CustomerService {
    public final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public List<CustomerDTO> findAll() {
        try {
            List<CustomerEntity> customerEntityList = this.customerRepository.findAll();
            if (CollectionUtils.isEmpty(customerEntityList)) {
                return Collections.emptyList();
            }
            return this.customerMapper.mapToListDto(customerEntityList);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public CustomerDTO findByPetId(long petId) {
        try {
            CustomerEntity customer = petRepository.getOne(petId).getCustomer();
            if (Objects.isNull(customer)) {
                throw new ResourceNotFoundException("Customer", "petId", String.valueOf(petId));
            }
            return this.customerMapper.mapToDto(customer);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public CustomerDTO save(CustomerEntity customer, List<Long> petIds) {
        try {

            Set<PetEntity> pets = new HashSet<>();
            if (CollectionUtils.isEmpty(petIds)) {
                customer.setPets(pets);
                return this.customerMapper .mapToDto(this.customerRepository.save(customer));
            }

            for (Long petId : petIds) {
                PetEntity pet = this.petRepository.getOne(petId);
                if (Objects.nonNull(pet)) {
                    pets.add(pet);
                }
            }
            customer.setPets(pets);
            return this.customerMapper.mapToDto(this.customerRepository.save(customer));

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageFormat
                    .format(CritterAppConstant.MessagePattem.SAVE_ERROR, CritterAppConstant.TableName.SCHEDULE), ex);
        }

    }
}
