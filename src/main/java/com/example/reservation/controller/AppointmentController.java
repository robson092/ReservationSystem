package com.example.reservation.controller;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.model.Appointment;
import com.example.reservation.service.AppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentServiceImpl service;

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments());
    }

    @GetMapping
    ResponseEntity<List<AppointmentDTO>> getAllAppointments(Pageable page) {
        return ResponseEntity.ok(service.getAllAppointmentsWithPage(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<AppointmentDTO> getAppointment(@PathVariable int id) {
        return service.getAppointment(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/done")
    ResponseEntity<List<AppointmentDTO>> getDoneAppointment(@RequestParam boolean state) {
        return ResponseEntity.ok(service.getDoneAppointments(state));
    }

    @PostMapping
    ResponseEntity<AppointmentDTO> addAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO appointment = service.save(appointmentDTO);
        return ResponseEntity
                .created(URI.create("/" + appointment.getId()))
                .body(appointment);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAppointment(@PathVariable int id) {
        if(!service.isAppointmentExist(id)) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.deleteAppointment(id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> setAppointmentStatus(@PathVariable int id) {
        service.setAppointmentDone(id);
        return ResponseEntity.noContent().build();
    }
}
