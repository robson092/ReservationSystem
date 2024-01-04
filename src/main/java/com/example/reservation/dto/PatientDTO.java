package com.example.reservation.dto;

import com.example.reservation.model.Patient;
import lombok.Getter;

import java.util.Set;

@Getter
public class PatientDTO {
    private final int id;

    private final String name;

    private final String surname;

    private final String city;
    private final String street;

    private final int streetNum;

    private final int postalCode;

    private final String email;

    private final Set<AppointmentFromPatientPovDTO> appointments;

    public PatientDTO(Patient source) {
        this.id = source.getId();
        this.name = source.getName();
        this.surname = source.getSurname();
        this.city = source.getCity();
        this.street = source.getStreet();
        this.streetNum = source.getStreetNum();
        this.postalCode = source.getPostalCode();
        this.email = source.getEmail();
        this.appointments = source.getAppointmentsForPatientDTO(source.getAppointments());
    }
}
