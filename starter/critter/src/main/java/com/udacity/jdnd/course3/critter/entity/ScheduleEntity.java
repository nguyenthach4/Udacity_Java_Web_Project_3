package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * ScheduleEntity.
 *
 * @author NguyenT4.
 * @since 2023/10/23.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CritterAppConstant.TableName.SCHEDULE)
public class ScheduleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(targetEntity = EmployeeEntity.class)
    private Set<EmployeeEntity> employees = new HashSet<>();

    @ManyToMany(targetEntity = PetEntity.class)
    private Set<PetEntity> pets = new HashSet<>();

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;
}
