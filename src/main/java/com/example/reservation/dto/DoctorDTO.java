package com.example.reservation.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private int id;
    private String name;
    private String surname;
    private Set<SpecializationFromDoctorPovDTO> specializations;
    private Set<AppointmentFromDoctorPovDTO> appointments;
    private Set<HospitalFromDoctorPovDTO> hospitalAffiliations;
}
