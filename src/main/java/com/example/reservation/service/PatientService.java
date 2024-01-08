package com.example.reservation.service;

import com.example.reservation.dto.PatientDTO;
import com.example.reservation.repository.PatientRepository;
import com.example.reservation.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;

    public Optional<PatientDTO> getPatient(Integer id) {
        return repository.findById(id)
                .map(PatientDTO::new);
    }

    public List<PatientDTO> getAllPatients() {
        return repository.findAll().stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> getAllPatientsWithPage(Pageable page) {
        return repository.findAll(page).getContent().stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
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
