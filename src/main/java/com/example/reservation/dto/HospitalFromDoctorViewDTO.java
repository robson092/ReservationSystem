package com.example.reservation.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter @Setter
public class HospitalFromDoctorViewDTO {

    private String hospitalName;
    private String city;
    private String street;

}
