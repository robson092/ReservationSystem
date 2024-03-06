package com.example.reservation.controller;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.dto.DoctorAvailabilityDTO;
import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.DoctorUpdateDTO;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors/search/availability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService doctorAvailabilityService;

    @GetMapping("/{id}")
    ResponseEntity<DoctorAvailability> getAvailability(@PathVariable int id) {
        return doctorAvailabilityService.getAvailability(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<List<DoctorAvailability>> getAllDoctorAvailabilities() {
        return ResponseEntity.ok(doctorAvailabilityService.getAllAvailabilities());
    }

    @GetMapping("/day")
    ResponseEntity<List<DoctorAvailability>> getDoctorsAvailabilitiesByDay(@RequestParam("v") String day) {
        return ResponseEntity.ok(doctorAvailabilityService.getAvailAbilitiesByDayOfWeek(day));
    }

    @GetMapping("/doctor/{id}")
    ResponseEntity<List<DoctorAvailabilityDTO>> getDoctorAvailabilities(@PathVariable int id) {
        return ResponseEntity.ok(doctorAvailabilityService.getAvailAbilitiesByDoctor(id));
    }

    @PostMapping
    ResponseEntity<DoctorAvailabilityDTO> addAvailability(@Valid @RequestBody DoctorAvailabilityDTO availabilityDTO) {
        DoctorAvailabilityDTO doctorAvailability = doctorAvailabilityService.saveAvailability(availabilityDTO);
        return ResponseEntity
                .created(URI.create("/" + doctorAvailability.getId()))
                .body(doctorAvailability);
    }

    @PutMapping("/{id}/update/hours")
    ResponseEntity<String> updateAvailabilityHours(@PathVariable int id, @RequestParam("start") String startTime,
                                                   @RequestParam("end") String endTime) {
        doctorAvailabilityService.updateHours(id, startTime, endTime);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/update/day")
    ResponseEntity<String> updateAvailabilityDays(@PathVariable int id, @RequestParam("v") String day) {
        doctorAvailabilityService.updateDays(id, day);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/update/availability")
    ResponseEntity<String> setAvailabilityStatus(@PathVariable int id, @RequestParam("v") boolean isAvailable) {
        doctorAvailabilityService.changeAvailableStatus(id, isAvailable);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteAvailability(@PathVariable int id) {
        doctorAvailabilityService.deleteAvailability(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
