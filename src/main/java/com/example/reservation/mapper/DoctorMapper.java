package com.example.reservation.mapper;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final SpecializationMapper specializationMapper;
    private final HospitalAffiliationMapper hospitalAffiliationMapper;

    public DoctorDTO mapToDto(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .specializations(specializationMapper.getSpecializationsDTOs(doctor))
                .build();
    }

    public Doctor mapToEntity(DoctorDTO dto) {
        return Doctor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .specializations(Optional.ofNullable(specializationMapper.getSpecializations(dto)).orElse(Collections.emptySet()))
                .build();
    }
}
