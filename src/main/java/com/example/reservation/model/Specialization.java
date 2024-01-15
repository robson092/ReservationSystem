package com.example.reservation.model;

import com.example.reservation.enums.SpecializationEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private SpecializationEnum specializationType;

    @ManyToMany(mappedBy = "specializations")
    private Set<Doctor> doctors ;
}
