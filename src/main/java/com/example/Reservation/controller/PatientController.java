package com.example.Reservation.controller;

import com.example.Reservation.service.PatientService;
import com.example.Reservation.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping
    ResponseEntity<List<Patient>> getAllPatients(Pageable page) {
        return ResponseEntity.ok(patientService.getAllPatientsWithPage(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<Patient> getPatient(@PathVariable int id) {
        return patientService.getPatient(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Patient> addPatient(@RequestBody Patient patientToCreate) {
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
