package com.example.reservation.dto;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateDTO {

    private int id;
    private String name;
    private String surname;
    private Set<Specialization> specializations;
}
