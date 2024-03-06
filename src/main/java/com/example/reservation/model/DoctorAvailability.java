package com.example.reservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

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
    private DayOfWeek dayOfWeek;
    @NotNull(message = "Start time is mandatory.")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "End time is mandatory.")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;
    private boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
