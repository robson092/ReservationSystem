package com.example.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class AppointmentFromDoctorViewDTO {
    private final int appointmentId;
    private final LocalDateTime date;
    private final int patientId;
    private final String patientName;
    private final String patientSurname;
}
