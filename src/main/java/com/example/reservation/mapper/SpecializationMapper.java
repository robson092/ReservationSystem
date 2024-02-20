package com.example.reservation.mapper;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.SpecializationFromDoctorPovDTO;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.Specialization;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SpecializationMapper {

    public Set<SpecializationFromDoctorPovDTO> mapToDto(Doctor doctor) {
        return doctor.getSpecializations().stream()
                .map(SpecializationFromDoctorPovDTO::new)
                .collect(Collectors.toSet());
    }

    public Set<Specialization> mapToEntity(DoctorDTO dto) {
        return dto.getSpecializations().stream()
                .map(specialization -> new Specialization(specialization.getSpecializationType()))
                .collect(Collectors.toSet());
    }
}
