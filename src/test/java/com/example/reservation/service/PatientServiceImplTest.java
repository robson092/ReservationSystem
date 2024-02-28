package com.example.reservation.service;

import com.example.reservation.dto.PatientDTO;
import com.example.reservation.dto.PatientUpdateDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.PatientMapper;
import com.example.reservation.mapper.PatientUpdateMapper;
import com.example.reservation.model.Appointment;
import com.example.reservation.model.Patient;
import com.example.reservation.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    PatientRepository mockPatientRepo;

    @Mock
    PatientMapper mockPatientMapper;

    @Mock
    PatientUpdateMapper mockUpdateMapper;

    @InjectMocks
    PatientServiceImpl patientService;

    Patient patient;
    PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patientDTO = new PatientDTO();
    }

    @Test
    @DisplayName("Should return patient when getPatient invoke")
    void when_getPatient_with_correct_id_should_return_patient() {
        patient.setId(1);

        when(mockPatientRepo.findById(1)).thenReturn(Optional.of(patient));
        when(mockPatientMapper.mapToDto(patient)).thenReturn(patientDTO);
        PatientDTO expected = patientService.getPatient(1).get();

        assertThat(expected).isSameAs(patientDTO);
        verify(mockPatientRepo).findById(1);
    }

    @Test
    @DisplayName("Throw illegalArgumentException when id is incorrect")
    void when_getPatient_with_incorrect_id_then_throw_illegalArgumentException() {
        when(mockPatientRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.getPatient(1));
    }

    @Test
    @DisplayName("Should return all patients when getAll method invoke")
    void when_getAllPatients_then_return_list_with_all_patients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        List<PatientDTO> patientDTOs = new ArrayList<>();
        patientDTOs.add(patientDTO);

        when(mockPatientRepo.findAll()).thenReturn(patients);
        when(mockPatientMapper.mapToDto(patient)).thenReturn(patientDTO);
        List<PatientDTO> expected = patientService.getAllPatients();

        assertEquals(expected, patientDTOs);
        verify(mockPatientRepo).findAll();
    }

    @Test
    @DisplayName("Should return all patients based on specific page size")
    void when_getAllPatients_then_return_list_of_patients_based_on_page_size() {
        List<Patient> patients = new ArrayList<>();
        Page<Patient> pageablePatients = new PageImpl<>(patients);
        Pageable pageable = mock(Pageable.class);

        when(mockPatientRepo.findAll(pageable)).thenReturn(pageablePatients);
        patientService.getAllPatientsWithPage(pageable);

        verify(mockPatientRepo).findAll(pageable);
    }

    @Test
    @DisplayName("Should return patient when save invoke")
    void when_save_then_return_patient() {
        patientDTO.setId(1);
        patient.setId(1);
        when(mockPatientRepo.save(patient)).thenAnswer(i -> i.getArguments()[0]);
        when(mockPatientMapper.mapToDto(patient)).thenReturn(patientDTO);
        when(mockPatientMapper.mapToEntity(patientDTO)).thenReturn(patient);

        PatientDTO expected = patientService.save(patientDTO);

        assertThat(expected).isSameAs(patientDTO);
        verify(mockPatientRepo).save(patient);
    }

    @Test
    @DisplayName("Should delete patient if id is correct")
    void when_deletePatient_with_correct_id_then_delete() throws CannotDeleteException {
        patient.setId(1);

        when(mockPatientRepo.findById(patient.getId())).thenReturn(Optional.of(patient));
        patientService.deletePatient(patient.getId());

        verify(mockPatientRepo).deleteById(patient.getId());
    }

    @Test
    @DisplayName("Should throw illegalArgumentException when incorrect id provided")
    void when_deletePatient_with_incorrect_id_then_throw_illegalArgumentException() {
        when(mockPatientRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.deletePatient(1));
        verify(mockPatientRepo).findById(1);
    }

    @Test
    @DisplayName("Should throw CannotDeleteException when attempt to delete patient with appointments scheduled")
    void when_deletePatient_on_patient_with_appointments_scheduled_then_throw_CannotDeleteException() throws CannotDeleteException {
        patient.setAppointments(Set.of(new Appointment()));
        patient.setId(1);

        when(mockPatientRepo.findById(patient.getId())).thenReturn(Optional.of(patient));

        assertThrows(CannotDeleteException.class, () -> patientService.deletePatient(patient.getId()));
    }

    @Test
    @DisplayName("Should update patient when provided correct patient id")
    void when_updatePatient_with_correct_patient_id_then_patient_will_update_with_patientDTO_fields() {
        var patientUpdateDTO = new PatientUpdateDTO();
        patientUpdateDTO.setName("dto name");
        patientUpdateDTO.setId(1);
        patient.setName("entity name");
        patient.setId(1);

        when(mockPatientRepo.findById(1)).thenReturn(Optional.of(patient));
        patientService.updatePatient(1, patientUpdateDTO);

        verify(mockPatientRepo).findById(1);
        verify(mockPatientRepo).save(patient);
    }

    @Test
    @DisplayName("Should throw illegalArgumentException when provide incorrect id")
    void when_updatePatient_with_incorrect_id_then_throw_illegalArgumentException() {
        var patientUpdateDTO = new PatientUpdateDTO();
        when(mockPatientRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatient(1, patientUpdateDTO));
    }
}
