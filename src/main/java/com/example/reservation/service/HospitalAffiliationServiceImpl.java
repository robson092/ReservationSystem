package com.example.reservation.service;

import com.example.reservation.dto.HospitalAffiliationDTO;
import com.example.reservation.mapper.HospitalAffiliationMapper;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import com.example.reservation.repository.DoctorRepository;
import com.example.reservation.repository.HospitalAffiliationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalAffiliationServiceImpl implements HospitalAffiliationService {

    private final HospitalAffiliationRepository hospitalAffiliationRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalAffiliationMapper mapper;
    @Override
    public List<HospitalAffiliationDTO> findAll() {
        List<HospitalAffiliation> hospitalAffiliations = hospitalAffiliationRepository.findAll();
        return hospitalAffiliations.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HospitalAffiliationDTO> findById(Integer id) {
        HospitalAffiliation hospitalAffiliation = hospitalAffiliationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        return Optional.ofNullable(mapper.mapToDto(hospitalAffiliation));
    }

    @Override
    public List<HospitalAffiliationDTO> findByDoctor(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        List<HospitalAffiliation> hospitalAffiliations = hospitalAffiliationRepository.findByDoctors(doctor);
        return hospitalAffiliations.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HospitalAffiliationDTO save(HospitalAffiliationDTO hospitalAffiliationDTO) {
        HospitalAffiliation hospitalAffiliation = mapper.mapToEntity(hospitalAffiliationDTO);
        HospitalAffiliation savedHospital = hospitalAffiliationRepository.save(hospitalAffiliation);
        return mapper.mapToDto(savedHospital);
    }
}
