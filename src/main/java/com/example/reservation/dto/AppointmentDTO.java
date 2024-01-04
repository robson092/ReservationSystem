package com.example.reservation.dto;

import com.example.reservation.model.Appointment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppointmentDTO {
    private int id;
    private LocalDateTime date;
    private int patientID;
    private String patientName;
    private String patientSurname;
    private int doctorId;
    private String doctorName;
    private String doctorSurname;
    private String doctorSpecialization;

    public AppointmentDTO(Appointment source) {
        this.id = source.getId();
        this.date = source.getDate();
        this.patientID = source.getPatient().getId();
        this.patientName = source.getPatient().getName();
        this.patientSurname = source.getPatient().getSurname();
        this.doctorId = source.getDoctor().getId();
        this.doctorName = source.getDoctor().getName();
        this.doctorSurname = source.getDoctor().getSurname();
        this.doctorSpecialization = source.getDoctor().getSpecialization();
    }
}
