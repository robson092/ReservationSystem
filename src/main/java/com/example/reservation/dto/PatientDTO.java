package com.example.reservation.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private int id;

    private String name;

    private String surname;

    private String city;
    private String street;

    private int streetNum;

    private int postalCode;

    private String email;

    private Set<AppointmentFromPatientViewDTO> appointments;

}
