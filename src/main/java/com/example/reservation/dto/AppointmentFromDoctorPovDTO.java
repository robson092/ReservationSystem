package com.example.reservation.dto;

import com.example.reservation.model.Appointment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppointmentFromDoctorPovDTO {
    private final int appointmentId;
    private final LocalDateTime date;
    private final int patientId;
    private final String patientName;
    private final String patientSurname;

    public AppointmentFromDoctorPovDTO(Appointment source) {
        this.appointmentId = source.getId();
        this.date = source.getDate();
        this.patientId = source.getDoctor().getId();
        this.patientName = source.getDoctor().getName();
        this.patientSurname = source.getDoctor().getSurname();
    }
}
