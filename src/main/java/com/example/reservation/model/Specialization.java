package com.example.reservation.model;

import com.example.reservation.enums.SpecializationEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "specializations")
@Getter @Setter
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private SpecializationEnum specializationType;

    @ManyToMany(mappedBy = "specializations")
    private Set<Doctor> doctors ;

    public Specialization() {
    }

    public Specialization(SpecializationEnum specializationType) {
        this.specializationType = specializationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialization)) return false;
        Specialization that = (Specialization) o;
        return id == that.id && specializationType == that.specializationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specializationType);
    }
}
