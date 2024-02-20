package com.example.reservation.controller;

import com.example.reservation.dto.PatientDTO;
import com.example.reservation.dto.PatientUpdateDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.service.PatientServiceImpl;
import com.example.reservation.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientService;

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
    ResponseEntity<PatientDTO> addPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO patient = patientService.save(patientDTO);
        return ResponseEntity
                .created(URI.create("/" + patient.getId()))
                .body(patient);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePatient(@PathVariable int id) throws CannotDeleteException {
        return ResponseEntity.ok(patientService.deletePatient(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updatePatient(@PathVariable int id, @RequestBody PatientUpdateDTO patientDto) {
        patientService.updatePatient(id, patientDto);
        return ResponseEntity.noContent().build();
    }
}
