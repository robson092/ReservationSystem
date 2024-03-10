package com.example.reservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "doctor_availability")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Date is mandatory.")
    private LocalDate date;
    @NotNull(message = "Start time is mandatory.")
    private LocalTime startTime;
    @NotNull(message = "End time is mandatory.")
    private LocalTime endTime;
    private boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "hospitalAffiliation_id")
    private HospitalAffiliation hospitalAffiliation;

}
