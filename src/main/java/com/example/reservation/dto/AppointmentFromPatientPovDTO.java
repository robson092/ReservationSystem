package com.example.reservation.dto;

import com.example.reservation.model.Appointment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppointmentFromPatientPovDTO {
    private final int appointmentId;
    private final LocalDateTime date;
    private final int doctorId;
    private final String doctorName;
    private final String doctorSurname;

    public AppointmentFromPatientPovDTO(Appointment source) {
        this.appointmentId = source.getId();
        this.date = source.getDate();
        this.doctorId = source.getDoctor().getId();
        this.doctorName = source.getDoctor().getName();
        this.doctorSurname = source.getDoctor().getSurname();
    }
}
