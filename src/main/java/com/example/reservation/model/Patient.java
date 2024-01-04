package com.example.reservation.model;


import com.example.reservation.dto.AppointmentFromPatientPovDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
@Entity
@Table(name = "patients", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})})
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String city;
    private String street;

    @Column(name = "street_num")
    private int streetNum;

    @Column(name = "postal_code")
    private int postalCode;

    private String email;
    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;

    public Patient(String name, String surname, String city, String street, int streetNum, int postalCode, String email) {
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.street = street;
        this.streetNum = streetNum;
        this.postalCode = postalCode;
        this.email = email;
    }

    public Set<AppointmentFromPatientPovDTO> getAppointmentsForPatientDTO(Set<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentFromPatientPovDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNum=" + streetNum +
                ", postalCode=" + postalCode +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id && streetNum == patient.streetNum && postalCode == patient.postalCode && Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(city, patient.city) && Objects.equals(street, patient.street) && Objects.equals(email, patient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, city, street, streetNum, postalCode, email);
    }
}
