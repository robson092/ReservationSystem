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
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Override
    public Optional<AppointmentDTO> getAppointment(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Appointment not found");
        }
        return repository.findById(id)
                .map(AppointmentDTO::new);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return repository.findAll().stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAllAppointmentsWithPage(Pageable page) {
        return repository.findAll(page).getContent().stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getDoneAppointments(boolean done) {
        return repository.findByDone(done).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Appointment save(Appointment appointment) {
        return repository.save(appointment);
    }

    @Override
    public Optional<Appointment> deleteAppointment(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Appointment not found");
        }
        return repository.deleteById(id);
    }

    @Override
    public boolean isAppointmentExist(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public void setAppointmentDone(int id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found."));
        appointment.setDone(true);
        repository.save(appointment);
    }

}
