package com.example.reservation.service;

import com.example.reservation.dto.HospitalAffiliationDTO;

import java.util.List;
import java.util.Optional;

public interface HospitalAffiliationService {
    List<HospitalAffiliationDTO> findAll();
    Optional<HospitalAffiliationDTO> findById(Integer id);
    List<HospitalAffiliationDTO> findByDoctor(Integer id);
    HospitalAffiliationDTO save(HospitalAffiliationDTO hospitalAffiliationDTO);
}
