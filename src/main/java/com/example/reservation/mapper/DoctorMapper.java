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
    private final AppointmentFromDoctorViewMapper appointmentFromDoctorViewMapper;

    public DoctorDTO mapToDto(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .specializations(specializationMapper.getSpecializationsDTOs(doctor))
                .appointments(appointmentFromDoctorViewMapper.getAppointmentsFromDoctorViewDTOs(doctor))
                .hospitalAffiliations(hospitalAffiliationMapper.getSetOfHospitalAffiliationDTO(doctor))
                .build();
    }

    public Doctor mapToEntity(DoctorDTO dto) {
        return Doctor.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .specializations(specializationMapper.getSpecializations(dto))
                .hospitalAffiliations(hospitalAffiliationMapper.getSetOfHospitalAffiliation(dto))
                .build();
    }
}
