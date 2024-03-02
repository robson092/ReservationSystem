package com.example.reservation.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "doctors", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is mandatory.")
    private String name;
    @NotBlank(message = "Surname is mandatory.")
    private String surname;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "doctor_specialization",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "specialization_id")}
    )
    @NotEmpty
    private Set<Specialization> specializations;
    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "doctor_hospital_affiliations",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "hospital_affiliations_id")}
    )
    @NotEmpty
    private Set<HospitalAffiliation> hospitalAffiliations;

    @OneToOne
    @JoinColumn(name = "doctor_availability_id")
    private DoctorAvailability availability;

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", specializations=" + specializations +
                ", appointments=" + appointments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id && Objects.equals(name, doctor.name) && Objects.equals(surname, doctor.surname) && Objects.equals(specializations, doctor.specializations) && Objects.equals(appointments, doctor.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, specializations, appointments);
    }
}
