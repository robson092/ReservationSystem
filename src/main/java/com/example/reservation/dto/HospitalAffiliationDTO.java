package com.example.reservation.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class HospitalAffiliationDTO {

    private int id;
    private String hospitalName;
    private String city;
    private String street;
    private int timeSlotPerClientInMinute;

}
