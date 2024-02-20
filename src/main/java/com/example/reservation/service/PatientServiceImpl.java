package com.example.reservation.service;

import com.example.reservation.dto.AppointmentFromPatientPovDTO;
import com.example.reservation.dto.PatientDTO;
import com.example.reservation.dto.PatientUpdateDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.PatientMapper;
import com.example.reservation.mapper.PatientUpdateMapper;
import com.example.reservation.model.Appointment;
import com.example.reservation.repository.PatientRepository;
import com.example.reservation.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientUpdateMapper updateMapper;
    private final PatientMapper mapper;

    @Override
    public Optional<PatientDTO> getPatient(Integer id) {
        Patient patient = repository.findById(id).
                orElseThrow(IllegalArgumentException::new);
        return Optional.ofNullable(mapper.mapToDto(patient));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = new ArrayList<>(repository.findAll());
        return patients.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientDTO> getAllPatientsWithPage(Pageable page) {
        List<Patient> patients = new ArrayList<>(repository.findAll(page).getContent());
        return patients.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        Patient patient = mapper.mapToEntity(patientDTO);
        Patient savedPatient = repository.save(patient);
        return mapper.mapToDto(savedPatient);
    }

    @Override
    public Optional<Patient> deletePatient(int id) throws CannotDeleteException {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Patient not found.");
        }
        Patient patient = repository.findById(id).orElse(null);
        if(!allowToDeletePatient(patient)) {
            List<Integer> appointmentsIDs = getPatientAppointmentsID(patient);
            throw new CannotDeleteException("Cannot delete Patient due to appointment scheduled. Appointments id: " + appointmentsIDs);
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
        updateMapper.updatePatientFromDto(patientDTO, patient);
        repository.save(patient);
    }

    private boolean allowToDeletePatient(Patient patient) {
        Set<Appointment> appointments = Optional.ofNullable(patient.getAppointments())
                .orElseGet(Collections::emptySet);
        return appointments.isEmpty();
    }

    private List<Integer> getPatientAppointmentsID(Patient patient) {
        return patient.getAppointments().stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());
    }

}
