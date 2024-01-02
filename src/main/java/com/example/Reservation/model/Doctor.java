package com.example.Reservation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "doctors", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@NotEmpty
    private String name, surname, specialization;



    public Doctor(String name, String surname, String specialization) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;

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
