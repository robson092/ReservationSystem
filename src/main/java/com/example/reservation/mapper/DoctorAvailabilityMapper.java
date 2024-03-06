package com.example.reservation.mapper;

import com.example.reservation.dto.DoctorAvailabilityDTO;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoctorAvailabilityMapper {

    private final DoctorRepository doctorRepository;
    public DoctorAvailabilityDTO mapToDto(DoctorAvailability doctorAvailability) {
        return DoctorAvailabilityDTO.builder()
                .id(doctorAvailability.getId())
                .dayOfWeek(doctorAvailability.getDayOfWeek())
                .startTime(doctorAvailability.getStartTime())
                .endTime(doctorAvailability.getEndTime())
                .isAvailable(doctorAvailability.isAvailable())
                .doctorId(doctorAvailability.getDoctor().getId())
                .build();
    }

    public DoctorAvailability mapToEntity(DoctorAvailabilityDTO dto) {
        return DoctorAvailability.builder()
                .dayOfWeek(dto.getDayOfWeek())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .isAvailable(dto.isAvailable())
                .doctor(doctorRepository.findById(dto.getDoctorId()).get())
                .build();
    }
}
