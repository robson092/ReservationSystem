package com.example.reservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "appointments", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "doctor_id"})})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@NotNull
    @NotNull(message = "Date is mandatory.")
    @Future(message = "Date must be in future.")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @NotNull
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @NotNull
    private Doctor doctor;

    public Appointment(Patient patient, Doctor doctor, LocalDateTime date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient=" + patient +
                ", doctor=" + doctor +
                '}';
    }
}
