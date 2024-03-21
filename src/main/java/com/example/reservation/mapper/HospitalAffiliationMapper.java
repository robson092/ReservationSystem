package com.example.reservation.mapper;

import com.example.reservation.dto.HospitalAffiliationDTO;
import com.example.reservation.model.HospitalAffiliation;
import org.springframework.stereotype.Component;

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
}
