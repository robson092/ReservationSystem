package com.example.reservation.service;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.mapper.AppointmentMapper;
import com.example.reservation.model.Appointment;
import com.example.reservation.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    @Override
    public Optional<AppointmentDTO> getAppointment(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Appointment not found");
        }
        Appointment appointment = repository.findById(id).get();
        return Optional.ofNullable(mapper.mapToDto(appointment));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>(repository.findAll());
        return appointments.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAllAppointmentsWithPage(Pageable page) {
        ArrayList<Appointment> appointments = new ArrayList<>(repository.findAll(page).getContent());
        return appointments.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getDoneAppointments(boolean done) {
        List<Appointment> appointments = new ArrayList<>(repository.findByDone(done));
        return appointments.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        Appointment appointment = mapper.mapToEntity(appointmentDTO);
        Appointment savedAppointment = repository.save(appointment);
        return mapper.mapToDto(savedAppointment);
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
