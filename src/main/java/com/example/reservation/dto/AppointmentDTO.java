package com.example.reservation.dto;

import com.example.reservation.model.Specialization;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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

}
