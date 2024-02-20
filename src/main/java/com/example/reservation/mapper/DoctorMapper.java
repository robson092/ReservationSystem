package com.example.reservation.mapper;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                .specializations(specializationMapper.mapToDto(doctor))
                .appointments(doctor.getAppointmentsForDoctorDTO(doctor.getAppointments()))
                .hospitalAffiliations(doctor.getHospitalForDoctorDTO(doctor.getHospitalAffiliations()))
                .build();
    }

    public Doctor mapToEntity(DoctorDTO dto) {
        return Doctor.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .specializations(specializationMapper.mapToEntity(dto))
                .hospitalAffiliations(hospitalAffiliationMapper.mapToEntity(dto))
                .build();
    }
}
