package com.example.reservation.service;

import com.example.reservation.dto.PatientDTO;
import com.example.reservation.dto.PatientUpdateDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.model.Patient;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    Optional<PatientDTO> getPatient(Integer id);
    List<PatientDTO> getAllPatients();
    List<PatientDTO> getAllPatientsWithPage(Pageable page);
    Patient save(Patient patient);
    Optional<Patient> deletePatient(int id) throws CannotDeleteException;
    boolean isPatientExist(Integer id);
    void updatePatient(Integer id, PatientUpdateDTO patientDTO);
}
