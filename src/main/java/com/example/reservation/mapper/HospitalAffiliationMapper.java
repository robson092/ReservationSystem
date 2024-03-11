package com.example.reservation.mapper;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.HospitalAffiliationDTO;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HospitalAffiliationMapper {

    public HospitalAffiliationDTO mapToDto(HospitalAffiliation hospitalAffiliation) {
        return HospitalAffiliationDTO.builder()
                .id(hospitalAffiliation.getId())
                .hospitalName(hospitalAffiliation.getHospitalName())
                .city(hospitalAffiliation.getCity())
                .street(hospitalAffiliation.getStreet())
                .timeSlotPerClientInMinute(hospitalAffiliation.getTimeSlotPerClientInMinute())
                .build();
    }

    public HospitalAffiliation mapToEntity(HospitalAffiliationDTO dto) {
        return HospitalAffiliation.builder()
                .id(dto.getId())
                .hospitalName(dto.getHospitalName())
                .city(dto.getCity())
                .street(dto.getStreet())
                .timeSlotPerClientInMinute(dto.getTimeSlotPerClientInMinute())
                .build();
    }

    public Set<HospitalAffiliationDTO> getSetOfHospitalAffiliationDTO(Doctor doctor) {
        return doctor.getHospitalAffiliations().stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    public Set<HospitalAffiliation> getSetOfHospitalAffiliation(DoctorDTO dto) {
        return dto.getHospitalAffiliations().stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }
}
