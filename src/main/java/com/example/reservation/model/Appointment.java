package com.example.reservation.model;

import com.example.reservation.dto.AppointmentFromPatientPovDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    //@JsonIgnore
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    //@JsonIgnore
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
