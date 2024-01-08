package com.example.reservation.controller;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.model.Doctor;
import com.example.reservation.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(service.getAllDoctor());
    }

    @GetMapping
    ResponseEntity<List<DoctorDTO>> getAllDoctors(Pageable page) {
        return ResponseEntity.ok(service.getAllDoctorsWithPage(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<DoctorDTO> getDoctor(@PathVariable int id) {
        return service.getDoctor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Doctor> addDoctor(@Valid @RequestBody Doctor doctorToCreate) {
        Doctor doctor = service.save(doctorToCreate);
        return ResponseEntity
                .created(URI.create("/" + doctor.getId()))
                .body(doctor);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDoctor(@PathVariable int id) {
        if(!service.isDoctorExist(id)) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.deleteDoctor(id));
    }
}
