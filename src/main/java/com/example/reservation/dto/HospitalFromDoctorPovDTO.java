package com.example.reservation.dto;

import com.example.reservation.model.HospitalAffiliation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class HospitalFromDoctorPovDTO {

    private String hospitalName;
    private String city;
    private String street;

    public HospitalFromDoctorPovDTO(HospitalAffiliation source) {
        this.hospitalName = source.getHospitalName();
        this.city = source.getCity();
        this.street = source.getStreet();
    }
}
