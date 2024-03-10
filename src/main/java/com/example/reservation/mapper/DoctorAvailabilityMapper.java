package com.example.reservation.mapper;

import com.example.reservation.dto.DoctorAvailabilityDTO;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.repository.DoctorRepository;
import com.example.reservation.repository.HospitalAffiliationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoctorAvailabilityMapper {

    private final DoctorRepository doctorRepository;
    private final HospitalAffiliationRepository hospitalAffiliationRepository;
    public DoctorAvailabilityDTO mapToDto(DoctorAvailability doctorAvailability) {
        return DoctorAvailabilityDTO.builder()
                .id(doctorAvailability.getId())
                .date(doctorAvailability.getDate())
                .startTime(doctorAvailability.getStartTime())
                .endTime(doctorAvailability.getEndTime())
                .isAvailable(doctorAvailability.isAvailable())
                .doctorId(doctorAvailability.getDoctor().getId())
                .hospitalAffiliationId(doctorAvailability.getHospitalAffiliation().getId())
                .build();
    }

    public DoctorAvailability mapToEntity(DoctorAvailabilityDTO dto) {
        return DoctorAvailability.builder()
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .isAvailable(dto.isAvailable())
                .doctor(doctorRepository.findById(dto.getDoctorId()).get())
                .hospitalAffiliation(hospitalAffiliationRepository.findById(dto.getHospitalAffiliationId()).get())
                .build();
    }
}
