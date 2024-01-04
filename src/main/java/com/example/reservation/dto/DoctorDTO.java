package com.example.reservation.dto;

import com.example.reservation.model.Doctor;
import lombok.Getter;

import java.util.Set;

@Getter
public class DoctorDTO {
    private int id;
    private String name;
    private String surname;
    private String specialization;
    private final Set<AppointmentFromDoctorPovDTO> appointments;

    public DoctorDTO(Doctor source) {
        this.id = source.getId();
        this.name = source.getName();
        this.surname = source.getSurname();
        this.specialization = source.getSpecialization();
        this.appointments = source.getAppointmentsForDoctorDTO(source.getAppointments());
    }
}
