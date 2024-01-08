package com.example.reservation.controller;

import com.example.reservation.dto.PatientDTO;
import com.example.reservation.service.PatientService;
import com.example.reservation.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping
    ResponseEntity<List<PatientDTO>> getAllPatients(Pageable page) {
        return ResponseEntity.ok(patientService.getAllPatientsWithPage(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<PatientDTO> getPatient(@PathVariable int id) {
        return patientService.getPatient(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patientToCreate) {
        Patient patient = patientService.save(patientToCreate);
        return ResponseEntity
                .created(URI.create("/" + patient.getId()))
                .body(patient);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePatient(@PathVariable int id) {
        if(!patientService.isPatientExist(id)) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patientService.deletePatient(id));
    }
}
