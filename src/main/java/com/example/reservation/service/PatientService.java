package com.example.reservation.service;

import com.example.reservation.repository.PatientRepository;
import com.example.reservation.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public Optional<Patient> getPatient(Integer id) {
        return repository.findById(id);
    }

    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public List<Patient> getAllPatientsWithPage(Pageable page) {
        return repository.findAll(page).getContent();
    }

    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    public Optional<Patient> deletePatient(int id) {
        return repository.deleteById(id);
    }

    public boolean isPatientExist(Integer id) {
        return repository.existsById(id);
    }


}
