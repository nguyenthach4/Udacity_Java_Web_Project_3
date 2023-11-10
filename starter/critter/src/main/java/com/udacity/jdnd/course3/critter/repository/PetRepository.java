package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * PetRepository.
 *
 * @author NguyenT4.
 * @since 2023/10/24.
 */
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findAllByCustomerId(Long customerId);
}
