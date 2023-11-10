package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.mapper.PetMapper;
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
 * PetService.
 *
 * @author NguyenT4.
 * @since 2023/10/27.
 */
@Service
@Transactional
public class PetService {
    public final Logger logger = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetMapper petMapper;

    public List<PetDTO> findAll() {
        try {
            List<PetEntity> petEntityList = this.petRepository.findAll();
            if (CollectionUtils.isEmpty(petEntityList)) {
                return Collections.emptyList();
            }
            return this.petMapper.mapToListDto(petEntityList);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public PetDTO findById(long petId) {
        try {
            PetEntity pet = this.petRepository.getOne(petId);
            if (Objects.isNull(pet)) {
                throw new ResourceNotFoundException("Pet", "petId", String.valueOf(petId));
            }
            return this.petMapper.mapToDto(pet);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public List<PetDTO> findByCustomerId(long customerId) {
        try {
            List<PetEntity> petEntityList = this.petRepository.findAllByCustomerId(customerId);
            if (Objects.isNull(petEntityList)) {
                throw new ResourceNotFoundException("Customer", "customerId", String.valueOf(customerId));
            }
            return this.petMapper.mapToListDto(petEntityList);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public PetDTO save(PetEntity pet, long ownerId) {
        try {
            CustomerEntity customer = customerRepository.getOne(ownerId);
            if (Objects.isNull(customer)) {
                throw new ResourceNotFoundException("Customer", "ownerId", String.valueOf(ownerId));
            }
            pet.setCustomer(customer);
            pet = petRepository.save(pet);

            Set<PetEntity> pets = new HashSet<>();
            pets.add(pet);
            customer.setPets(pets);
            customerRepository.save(customer);
            return this.petMapper.mapToDto(pet);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageFormat
                    .format(CritterAppConstant.MessagePattem.SAVE_ERROR, CritterAppConstant.TableName.PET), ex);
        }
    }
}
