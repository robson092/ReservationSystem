package com.example.reservation.model;

import com.example.reservation.dto.AppointmentFromDoctorPovDTO;
import com.example.reservation.dto.HospitalFromDoctorPovDTO;
import com.example.reservation.dto.SpecializationFromDoctorPovDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
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

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Set<HospitalAffiliation> hospitalAffiliations;

    public Doctor(String name, String surname, Set<Specialization> specialization,
                  Set<HospitalAffiliation> hospitalAffiliations) {
        this.name = name;
        this.surname = surname;
        this.specializations = specialization;
        this.hospitalAffiliations = hospitalAffiliations;

    }

    public Set<AppointmentFromDoctorPovDTO> getAppointmentsForDoctorDTO(Set<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentFromDoctorPovDTO::new)
                .collect(Collectors.toSet());
    }

    public Set<SpecializationFromDoctorPovDTO> getSpecializationsForDoctorDTO(Set<Specialization> specializations) {
        return specializations.stream()
                .map(SpecializationFromDoctorPovDTO::new)
                .collect(Collectors.toSet());
    }

    public Set<HospitalFromDoctorPovDTO> getHospitalForDoctorDTO(Set<HospitalAffiliation> hospitalAffiliations) {
        return hospitalAffiliations.stream()
                .map(HospitalFromDoctorPovDTO::new)
                .collect(Collectors.toSet());
    }

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
