package com.example.reservation.dto;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Appointment;
import com.example.reservation.model.Specialization;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
public class AppointmentDTO {
    private final int id;
    private final LocalDateTime date;
    private final PatientDTO patientDTO;
    private final DoctorDTO doctorDTO;
    private final Set<Specialization> doctorSpecialization;
    private final boolean done;

}
