package com.example.reservation.dto;

import com.example.reservation.model.Doctor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DoctorDTO {
    private int id;
    private String name;
    private String surname;
    private Set<SpecializationFromDoctorPovDTO> specialization;
    private Set<AppointmentFromDoctorPovDTO> appointments;
    private Set<HospitalFromDoctorPovDTO> hospitals;

    public DoctorDTO(Doctor source) {
        this.id = source.getId();
        this.name = source.getName();
        this.surname = source.getSurname();
        this.specialization = source.getSpecializationsForDoctorDTO(source.getSpecializations());
        this.appointments = source.getAppointmentsForDoctorDTO(source.getAppointments());
        this.hospitals = source.getHospitalForDoctorDTO(source.getHospitalAffiliations());
    }
}
