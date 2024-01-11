package com.example.reservation.service;

import com.example.reservation.dto.AppointmentFromDoctorPovDTO;
import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.model.Appointment;
import com.example.reservation.model.Doctor;
import com.example.reservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;

    public Optional<DoctorDTO> getDoctor(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Doctor not found");
        }
        return repository.findById(id)
                .map(DoctorDTO::new);
    }

    public List<DoctorDTO> getAllDoctor() {
        return repository.findAll().stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getAllDoctorsWithPage(Pageable page) {
        return repository.findAll(page).getContent().stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public Doctor save(Doctor doctor) {
        return repository.save(doctor);
    }

    public Optional<Doctor> deleteDoctor(int id) throws CannotDeleteException {
        if (!isDoctorExist(id)) {
            throw new IllegalArgumentException("Doctor not found");
        }
        DoctorDTO doctor = getDoctor(id).orElse(null);
        Set<AppointmentFromDoctorPovDTO> appointments = Optional.ofNullable(doctor.getAppointments())
                .orElseGet(Collections::emptySet);
        if (!appointments.isEmpty()) {
            List<Integer> ids = appointments.stream()
                    .map(AppointmentFromDoctorPovDTO::getAppointmentId)
                    .collect(Collectors.toList());
            throw new CannotDeleteException("Cannot delete Doctor due to appointment scheduled. Appointments id: " + ids);
        }
        return repository.deleteById(id);
    }

    public boolean isDoctorExist(Integer id) {
        return repository.existsById(id);
    }

}
