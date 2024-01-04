package com.example.reservation.controller;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.model.Appointment;
import com.example.reservation.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments().stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList()));
    }

    @GetMapping
    ResponseEntity<List<AppointmentDTO>> getAllAppointments(Pageable page) {
        return ResponseEntity.ok(service.getAllAppointmentsWithPage(page).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<AppointmentDTO> getAppointment(@PathVariable int id) {
        return service.getAppointment(id)
                .map(AppointmentDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointmentToCreate) {
        Appointment appointment = service.save(appointmentToCreate);
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
}
