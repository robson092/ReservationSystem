package com.example.reservation.controller;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.DoctorUpdateDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.service.DoctorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorServiceImpl service;

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

    @GetMapping("/specializations")
    ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialization(@RequestParam("value") String specialization) {
        return ResponseEntity.ok(service.getDoctorBySpecialization(specialization));
    }

    @PostMapping
    ResponseEntity<DoctorDTO> addDoctor(@Valid @RequestBody DoctorDTO doctorDto) {
        DoctorDTO doctor = service.save(doctorDto);
        return ResponseEntity
                .created(URI.create("/" + doctor.getId()))
                .body(doctor);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDoctor(@PathVariable int id) throws CannotDeleteException {
        return ResponseEntity.ok(service.deleteDoctor(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateDoctor(@PathVariable int id, @RequestBody DoctorUpdateDTO doctorDto) {
        service.updateDoctor(id, doctorDto);
        return ResponseEntity.noContent().build();
    }
}
