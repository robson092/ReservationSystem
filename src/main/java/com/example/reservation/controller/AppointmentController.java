package com.example.reservation.controller;

import com.example.reservation.model.Appointment;
import com.example.reservation.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments());
    }

    @GetMapping
    ResponseEntity<List<Appointment>> getAllAppointments(Pageable page) {
        return ResponseEntity.ok(service.getAllAppointmentsWithPage(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<Appointment> getAppointment(@PathVariable int id) {
        return service.getAppointment(id)
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
