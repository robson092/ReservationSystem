package com.example.reservation.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "appointments", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "doctor_id"})})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    @OneToOne
    private HospitalAffiliation hospitalAffiliation;

    private boolean done;

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", date=" + date +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", done=" + done +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return id == that.id && done == that.done && Objects.equals(date, that.date) && Objects.equals(patient, that.patient) && Objects.equals(doctor, that.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, patient, doctor, done);
    }
}
