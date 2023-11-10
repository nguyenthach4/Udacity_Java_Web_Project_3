package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import com.udacity.jdnd.course3.critter.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CustomerEntity.
 *
 * @author NguyenT4.
 * @since 2023/10/22.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CritterAppConstant.TableName.PET)
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private PetType type;

    private String name;

    @ManyToOne
    @JoinColumn(name = CritterAppConstant.CustomerTable.PERSON_ID,
            foreignKey = @ForeignKey(name = CritterAppConstant.CustomerTable.PERSON_ID_FK))
    private CustomerEntity customer;

    private LocalDate birthDate;

    private String notes;
}
