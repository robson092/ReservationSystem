package com.example.reservation.model;

import com.example.reservation.dto.AppointmentFromDoctorPovDTO;
import com.example.reservation.dto.AppointmentFromPatientPovDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "doctors", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@NotEmpty
    private String name;
    private String surname;
    private String specialization;
    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;



    public Doctor(String name, String surname, String specialization) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;

    }

    public Set<AppointmentFromDoctorPovDTO> getAppointmentsForDoctorDTO(Set<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentFromDoctorPovDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
