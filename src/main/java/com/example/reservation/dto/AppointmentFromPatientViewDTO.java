package com.example.reservation.dto;

import com.example.reservation.model.Appointment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class AppointmentFromPatientViewDTO {
    private int appointmentId;
    private LocalDateTime date;
    private int doctorId;
    private String doctorName;
    private String doctorSurname;


}
