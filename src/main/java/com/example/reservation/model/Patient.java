package com.example.reservation.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "patients", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotBlank(message = "Surname is mandatory.")
    private String surname;

    private String city;
    private String street;

    @Column(name = "street_num")
    private int streetNum;

    @Column(name = "postal_code")
    private int postalCode;

    @Email(message = "Invalid email.")
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
                ", appointments=" + appointments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return id == patient.id && streetNum == patient.streetNum && postalCode == patient.postalCode && Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(city, patient.city) && Objects.equals(street, patient.street) && Objects.equals(email, patient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, city, street, streetNum, postalCode, email);
    }
}
