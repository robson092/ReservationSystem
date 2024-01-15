package com.example.reservation.dto;

import com.example.reservation.model.Specialization;
import lombok.Getter;

@Getter
public class SpecializationFromDoctorPovDTO {

    private final String specialization;

    public SpecializationFromDoctorPovDTO(Specialization source) {
        this.specialization = source.getSpecializationType().getSpecialization();
    }
}
