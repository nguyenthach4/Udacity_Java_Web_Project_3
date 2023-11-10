package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CustomerRepository.
 *
 * @author NguyenT4.
 * @since 2023/10/24.
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
