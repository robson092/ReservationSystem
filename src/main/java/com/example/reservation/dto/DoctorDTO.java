package com.example.reservation.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private int id;
    private String name;
    private String surname;
    private Set<SpecializationFromDoctorViewDTO> specializations;
    private Set<AppointmentFromDoctorViewDTO> appointments;
    private Set<HospitalFromDoctorViewDTO> hospitalAffiliations;
}
