package com.example.reservation.service;

import com.example.reservation.model.Doctor;
import com.example.reservation.model.Patient;
import com.example.reservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;

    public Optional<Doctor> getDoctor(Integer id) {
        return repository.findById(id);
    }

    public List<Doctor> getAllDoctor() {
        return repository.findAll();
    }

    public List<Doctor> getAllDoctorsWithPage(Pageable page) {
        return repository.findAll(page).getContent();
    }

    public Doctor save(Doctor doctor) {
        return repository.save(doctor);
    }

    public Optional<Doctor> deleteDoctor(int id) {
        return repository.deleteById(id);
    }

    public boolean isDoctorExist(Integer id) {
        return repository.existsById(id);
    }

}
