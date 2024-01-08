package com.example.reservation.service;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.model.Appointment;
import com.example.reservation.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;

    public Optional<Appointment> getAppointment(Integer id) {
        return repository.findById(id);
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public List<Appointment> getAllAppointmentsWithPage(Pageable page) {
        return repository.findAll(page).getContent();
    }

    public List<AppointmentDTO> getDoneAppointments(boolean done) {
        return repository.findByDone(done).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public Appointment save(Appointment appointment) {
        return repository.save(appointment);
    }

    public Optional<Appointment> deleteAppointment(int id) {
        return repository.deleteById(id);
    }

    public boolean isAppointmentExist(Integer id) {
        return repository.existsById(id);
    }

}
