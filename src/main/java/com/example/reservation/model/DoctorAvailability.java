package com.example.reservation.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
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
    @NotNull(message = "Day is mandatory.")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @NotNull(message = "Start time is mandatory.")
    private LocalTime startTime;
    @NotNull(message = "End time is mandatory.")
    private LocalTime endTime;
    private boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
