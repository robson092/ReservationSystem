package com.example.reservation.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private int id;
    private LocalDateTime date;
    private PatientDTO patient;
    private DoctorDTO doctor;
    private boolean done;
    private HospitalAffiliationDTO hospital;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentDTO)) return false;
        AppointmentDTO that = (AppointmentDTO) o;
        return id == that.id && done == that.done && Objects.equals(date, that.date) && Objects.equals(patient, that.patient) && Objects.equals(doctor, that.doctor) && Objects.equals(hospital, that.hospital);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, patient, doctor, done, hospital);
    }
}
