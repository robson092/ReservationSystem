package com.example.reservation.controller;

import com.example.reservation.dto.HospitalAffiliationDTO;
import com.example.reservation.model.HospitalAffiliation;
import com.example.reservation.service.HospitalAffiliationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hospitals")
public class HospitalAffiliationController {

    private final HospitalAffiliationService hospitalAffiliationService;

    @GetMapping()
    ResponseEntity<List<HospitalAffiliationDTO>> getAllHospitals() {
        return ResponseEntity.ok(hospitalAffiliationService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<HospitalAffiliationDTO> getHospital(@PathVariable Integer id) {
        return hospitalAffiliationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
