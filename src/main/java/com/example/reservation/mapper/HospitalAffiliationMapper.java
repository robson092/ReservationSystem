package com.example.reservation.mapper;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.HospitalFromDoctorPovDTO;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HospitalAffiliationMapper {

    public Set<HospitalFromDoctorPovDTO> mapToDto(Doctor doctor) {
        return doctor.getHospitalAffiliations().stream()
                .map(HospitalFromDoctorPovDTO::new)
                .collect(Collectors.toSet());
    }

    public Set<HospitalAffiliation> mapToEntity(DoctorDTO dto) {
        return dto.getHospitalAffiliations().stream()
                .map(hospital -> new HospitalAffiliation(hospital.getHospitalName(), hospital.getCity(), hospital.getStreet()))
                .collect(Collectors.toSet());
    }
}
