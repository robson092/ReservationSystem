package com.example.reservation.dto;

import com.example.reservation.model.HospitalAffiliation;
import lombok.Getter;

@Getter
public class HospitalFromDoctorPovDTO {

    private final int hospitalId;
    private final String hospitalName;

    public HospitalFromDoctorPovDTO(HospitalAffiliation source) {
        this.hospitalId = source.getId();
        this.hospitalName = source.getHospitalName();
    }
}
