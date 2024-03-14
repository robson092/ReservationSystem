package com.example.reservation.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private int id;
    private String name;
    private String surname;
    private Set<SpecializationFromDoctorViewDTO> specializations;
    private Set<HospitalAffiliationDTO> hospitalAffiliations;

}
