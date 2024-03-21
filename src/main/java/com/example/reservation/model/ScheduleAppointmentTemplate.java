package com.example.reservation.model;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.HospitalAffiliationDTO;
import com.example.reservation.dto.PatientDTO;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ScheduleAppointmentTemplate {

    PatientDTO patientDTO;
    DoctorDTO doctorDTO;
    HospitalAffiliationDTO hospitalAffiliationDTO;
    LocalDateTime appointmentDateTime;
}
