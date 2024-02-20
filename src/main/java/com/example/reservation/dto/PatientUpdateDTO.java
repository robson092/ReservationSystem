package com.example.reservation.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PatientUpdateDTO {
    private int id;

    private String name;

    private String surname;

    private String city;
    private String street;

    private int streetNum;

    private int postalCode;

    private String email;
}
