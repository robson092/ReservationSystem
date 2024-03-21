package com.example.reservation.mapper;

import com.example.reservation.dto.PatientDTO;
import com.example.reservation.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientMapper {

    public PatientDTO mapToDto(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .city(patient.getCity())
                .street(patient.getStreet())
                .streetNum(patient.getStreetNum())
                .postalCode(patient.getPostalCode())
                .email(patient.getEmail())
                .build();
    }

    public Patient mapToEntity(PatientDTO dto) {
        return Patient.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .city(dto.getCity())
                .street(dto.getStreet())
                .streetNum(dto.getStreetNum())
                .postalCode(dto.getPostalCode())
                .email(dto.getEmail())
                .build();
    }
}
