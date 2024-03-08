package com.example.reservation.controller;

import com.example.reservation.dto.DoctorAvailabilityDTO;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors/availability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService doctorAvailabilityService;

    @GetMapping("/{id}")
    ResponseEntity<DoctorAvailability> getAvailability(@PathVariable int id) {
        return doctorAvailabilityService.getAvailability(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    ResponseEntity<List<DoctorAvailabilityDTO>> getAllDoctorAvailabilities() {
        return ResponseEntity.ok(doctorAvailabilityService.getAllAvailabilities());
    }

    @GetMapping("/search/day")
    ResponseEntity<List<DoctorAvailabilityDTO>> getDoctorsAvailabilitiesByDay(@RequestParam("v") String day) {
        return ResponseEntity.ok(doctorAvailabilityService.getAvailAbilitiesByDayOfWeek(day));
    }

    @GetMapping("/search/doctor/{id}")
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

    @PatchMapping("/{id}/hours")
    ResponseEntity<String> updateAvailabilityHours(@PathVariable int id, @RequestParam("start") String startTime,
                                                   @RequestParam("end") String endTime) {
        doctorAvailabilityService.updateHours(id, startTime, endTime);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/day")
    ResponseEntity<String> updateAvailabilityDays(@PathVariable int id, @RequestParam("v") String day) {
        doctorAvailabilityService.updateDays(id, day);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/availability")
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
