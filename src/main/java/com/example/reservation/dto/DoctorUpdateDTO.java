package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorUpdateDTO {

    private final int id;
    private final String name;
    private final String surname;
    private final String specialization;
}
