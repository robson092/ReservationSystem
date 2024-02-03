package com.example.reservation.dto;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class DoctorUpdateDTO {

    private final int id;
    private final String name;
    private final String surname;
    private final Set<Specialization> specializations;
}
