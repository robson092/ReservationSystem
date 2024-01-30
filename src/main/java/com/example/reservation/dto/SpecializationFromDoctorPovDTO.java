package com.example.reservation.dto;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Specialization;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class SpecializationFromDoctorPovDTO {

    private SpecializationEnum specializationType;


    public SpecializationFromDoctorPovDTO(Specialization source) {
        this.specializationType = source.getSpecializationType();
    }
}
