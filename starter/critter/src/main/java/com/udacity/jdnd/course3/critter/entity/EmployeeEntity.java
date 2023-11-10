package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;

/**
 * EmployeeEntity.
 *
 * @author NguyenT4.
 * @since 2023/10/23.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CritterAppConstant.TableName.EMPLOYEE)
public class EmployeeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;
}
