package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
@Table(name = CritterAppConstant.TableName.CUSTOMER)
public class CustomerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(targetEntity = PetEntity.class)
    private Set<PetEntity> pets = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof CustomerEntity) {
            final CustomerEntity customerEntity = (CustomerEntity) obj;
            return Objects.equals(this.id, customerEntity.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
