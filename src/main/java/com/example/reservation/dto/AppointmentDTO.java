package com.example.reservation.dto;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Appointment;
import com.example.reservation.model.Specialization;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class AppointmentDTO {
    private final int id;
    private final LocalDateTime date;
    private final int patientID;
    private final String patientName;
    private final String patientSurname;
    private final int doctorId;
    private final String doctorName;
    private final String doctorSurname;
    private final Set<Specialization> doctorSpecialization;
    private final boolean done;

    public AppointmentDTO(Appointment source) {
        this.id = source.getId();
        this.date = source.getDate();
        this.patientID = source.getPatient().getId();
        this.patientName = source.getPatient().getName();
        this.patientSurname = source.getPatient().getSurname();
        this.doctorId = source.getDoctor().getId();
        this.doctorName = source.getDoctor().getName();
        this.doctorSurname = source.getDoctor().getSurname();
        this.doctorSpecialization = source.getDoctor().getSpecializations();
        this.done = source.isDone();
    }
}
