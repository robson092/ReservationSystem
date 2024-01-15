package com.example.reservation.dto;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.Specialization;
import lombok.Getter;

import java.util.Set;

@Getter
public class DoctorDTO {
    private final int id;
    private final String name;
    private final String surname;
    private final Set<SpecializationFromDoctorPovDTO> specialization;
    private final Set<AppointmentFromDoctorPovDTO> appointments;

    public DoctorDTO(Doctor source) {
        this.id = source.getId();
        this.name = source.getName();
        this.surname = source.getSurname();
        this.specialization = source.getSpecializationsForDoctorDTO(source.getSpecializations());
        this.appointments = source.getAppointmentsForDoctorDTO(source.getAppointments());
    }
}
