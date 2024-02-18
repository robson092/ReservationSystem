package com.example.reservation.service;

import com.example.reservation.dto.AppointmentFromPatientPovDTO;
import com.example.reservation.dto.PatientDTO;
import com.example.reservation.dto.PatientUpdateDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.PatientMapper;
import com.example.reservation.repository.PatientRepository;
import com.example.reservation.model.Patient;
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
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    @Override
    public Optional<PatientDTO> getPatient(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Patient not found");
        }
        return repository.findById(id)
                .map(PatientDTO::new);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return repository.findAll().stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientDTO> getAllPatientsWithPage(Pageable page) {
        return repository.findAll(page).getContent().stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Optional<Patient> deletePatient(int id) throws CannotDeleteException {
        if (!isPatientExist(id)) {
            throw new IllegalArgumentException("Patient not found.");
        }
        PatientDTO patient = getPatient(id).orElse(null);
        Set<AppointmentFromPatientPovDTO> appointments = Optional.ofNullable(patient.getAppointments())
                .orElseGet(Collections::emptySet);
        if(!appointments.isEmpty()) {
            List<Integer> ids = appointments.stream()
                    .map(AppointmentFromPatientPovDTO::getAppointmentId)
                    .collect(Collectors.toList());
            throw new CannotDeleteException("Cannot delete Patient due to appointment scheduled. Appointments id: " + ids);
        }
        return repository.deleteById(id);
    }

    @Override
    public boolean isPatientExist(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public void updatePatient(Integer id, PatientUpdateDTO patientDTO) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found."));
        mapper.updatePatientFromDto(patientDTO, patient);
        repository.save(patient);
    }

}
