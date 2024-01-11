package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientUpdateDTO {
    private final int id;

    private final String name;

    private final String surname;

    private final String city;
    private final String street;

    private final int streetNum;

    private final int postalCode;

    private final String email;
}
