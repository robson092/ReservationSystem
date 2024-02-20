package com.example.reservation.model;

import com.example.reservation.enums.SpecializationEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "specializations")
@Getter @Setter
@RequiredArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private final SpecializationEnum specializationType;

    @ManyToMany(mappedBy = "specializations")
    private Set<Doctor> doctors ;

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
