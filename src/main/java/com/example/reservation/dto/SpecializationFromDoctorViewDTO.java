package com.example.reservation.dto;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Specialization;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class SpecializationFromDoctorViewDTO {

    private SpecializationEnum specializationType;


    public SpecializationFromDoctorViewDTO(Specialization source) {
        this.specializationType = source.getSpecializationType();
    }
}
