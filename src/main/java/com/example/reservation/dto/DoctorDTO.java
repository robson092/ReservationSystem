package com.example.reservation.dto;

import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import com.example.reservation.model.Specialization;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class DoctorDTO {
    private int id;
    private String name;
    private String surname;
    private Set<SpecializationFromDoctorPovDTO> specializations;
    private Set<AppointmentFromDoctorPovDTO> appointments;
    private Set<HospitalFromDoctorPovDTO> hospitalAffiliations;

    public DoctorDTO(Doctor source) {
        this.id = source.getId();
        this.name = source.getName();
        this.surname = source.getSurname();
        this.specializations = source.getSpecializationsForDoctorDTO(source.getSpecializations());
        this.appointments = source.getAppointmentsForDoctorDTO(source.getAppointments());
        this.hospitalAffiliations = source.getHospitalForDoctorDTO(source.getHospitalAffiliations());
    }
}
