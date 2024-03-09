package com.example.reservation.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class DoctorAvailabilityDTO {
    private int id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;
    private int doctorId;
}
