package com.example.reservation.service;

import com.example.reservation.dto.*;
import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.DoctorMapper;
import com.example.reservation.mapper.DoctorUpdateMapper;
import com.example.reservation.model.Appointment;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import com.example.reservation.model.Specialization;
import com.example.reservation.repository.DoctorRepository;
import com.example.reservation.repository.HospitalAffiliationRepository;
import com.example.reservation.repository.SpecializationRepository;
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

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    DoctorRepository mockDoctorRepo;

    @Mock
    SpecializationRepository mockSpecializationRepo;

    @Mock
    HospitalAffiliationRepository mockHospitalAffilRepo;

    @Mock
    DoctorMapper mockDoctorMapper;

    @Mock
    DoctorUpdateMapper updateMapper;

    @InjectMocks
    DoctorServiceImpl doctorService;

    Doctor doctor;
    DoctorDTO doctorDTO;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctorDTO = new DoctorDTO();
    }
    @Test
    @DisplayName("Should return doctor when getDoctor invoke")
    void when_getDoctor_with_correct_id_should_return_doctor() {
        doctor.setId(1);

        when(mockDoctorRepo.findById(1)).thenReturn(Optional.of(doctor));
        when(mockDoctorMapper.mapToDto(doctor)).thenReturn(doctorDTO);
        DoctorDTO expected = doctorService.getDoctor(1).get();

        assertThat(expected).isSameAs(doctorDTO);
        verify(mockDoctorRepo).findById(1);
    }

    @Test
    @DisplayName("Throw illegalArgumentException when id is incorrect")
    void when_getDoctor_with_incorrect_id_then_throw_illegalArgumentException() {
        when(mockDoctorRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> doctorService.getDoctor(1));
    }

    @Test
    @DisplayName("Should return all doctors when getAll method invoke")
    void when_getAllDoctors_then_return_list_with_all_doctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        List<DoctorDTO> doctorDTOs = new ArrayList<>();
        doctorDTOs.add(doctorDTO);

        when(mockDoctorRepo.findAll()).thenReturn(doctors);
        when(mockDoctorMapper.mapToDto(doctor)).thenReturn(doctorDTO);
        List<DoctorDTO> expected = doctorService.getAllDoctor();

        assertEquals(expected, doctorDTOs);
        verify(mockDoctorRepo).findAll();
    }

    @Test
    @DisplayName("Should return all doctors based on specific page size")
    void when_getAllDoctors_then_return_list_of_doctors_based_on_page_size() {
        List<Doctor> doctors = new ArrayList<>();
        Page<Doctor> pageableDoctors = new PageImpl<>(doctors);
        Pageable pageable = mock(Pageable.class);

        when(mockDoctorRepo.findAll(pageable)).thenReturn(pageableDoctors);
        doctorService.getAllDoctorsWithPage(pageable);

        verify(mockDoctorRepo).findAll(pageable);
    }

    @Test
    @DisplayName("Should return doctor when save invoke")
    void when_save_then_return_doctor() {
        doctorDTO.setId(1);
        doctorDTO.setSpecializations(Set.of(new SpecializationFromDoctorViewDTO()));
        doctorDTO.setHospitalAffiliations(Set.of(new HospitalFromDoctorViewDTO("testHospital", "testCity", "testStreet")));
        doctor.setId(1);
        doctor.setSpecializations(Set.of(new Specialization(any(SpecializationEnum.class))));
        doctor.setHospitalAffiliations(Set.of(new HospitalAffiliation()));
        when(mockDoctorRepo.save(doctor)).thenAnswer(i -> i.getArguments()[0]);
        when(mockDoctorMapper.mapToDto(doctor)).thenReturn(doctorDTO);
        when(mockDoctorMapper.mapToEntity(doctorDTO)).thenReturn(doctor);

        DoctorDTO expected = doctorService.save(doctorDTO);

        assertThat(expected).isSameAs(doctorDTO);
        verify(mockDoctorRepo).save(doctor);
    }

    @Test
    @DisplayName("Should delete doctor if id is correct")
    void when_deleteDoctor_with_correct_id_then_delete() throws CannotDeleteException {
        doctor.setId(1);

        when(mockDoctorRepo.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        doctorService.deleteDoctor(doctor.getId());

        verify(mockDoctorRepo).deleteById(doctor.getId());
    }

    @Test
    @DisplayName("Should throw illegalArgumentException when incorrect id provided")
    void when_deleteDoctor_with_incorrect_id_then_throw_illegalArgumentException() {
        when(mockDoctorRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> doctorService.deleteDoctor(1));
        verify(mockDoctorRepo).findById(1);
    }

    @Test
    @DisplayName("Should throw CannotDeleteException when attempt to delete doctor with appointments scheduled")
    void when_deleteDoctor_on_doctor_with_appointments_scheduled_then_throw_CannotDeleteException() throws CannotDeleteException {
        doctor.setAppointments(Set.of(new Appointment()));
        doctor.setId(1);

        when(mockDoctorRepo.findById(doctor.getId())).thenReturn(Optional.of(doctor));

        assertThrows(CannotDeleteException.class, () -> doctorService.deleteDoctor(doctor.getId()));
    }

    @Test
    @DisplayName("Should update doctor when provided correct doctor id")
    void when_updateDoctor_with_correct_doctor_id_then_doctor_will_update_with_doctorDTO_fields() {
        var doctorUpdateDTO = new DoctorUpdateDTO();
        doctorUpdateDTO.setName("dto name");
        doctorUpdateDTO.setId(1);
        doctor.setName("entity name");
        doctor.setId(1);

        when(mockDoctorRepo.findById(1)).thenReturn(Optional.of(doctor));
        doctorService.updateDoctor(1, doctorUpdateDTO);

        verify(mockDoctorRepo).findById(1);
        verify(mockDoctorRepo).save(doctor);
    }

    @Test
    @DisplayName("Should throw illegalArgumentException when provide incorrect id")
    void when_updateDoctor_with_incorrect_id_then_throw_illegalArgumentException() {
        var doctorUpdateDTO = new DoctorUpdateDTO();
        when(mockDoctorRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> doctorService.updateDoctor(1, doctorUpdateDTO));
    }

    @Test
    @DisplayName("Should return doctor with requested specialization")
    void when_getDoctorBySpecialization_with_specialization_return_doctor_with_this_specialization() {
        Specialization specialization = new Specialization(SpecializationEnum.CARDIOLOGIST);
        SpecializationFromDoctorViewDTO specializationDTO = new SpecializationFromDoctorViewDTO(specialization);
        doctor.setSpecializations(Set.of(specialization));
        doctorDTO.setSpecializations(Set.of(specializationDTO));
        List<Specialization> specializations = new ArrayList<>();
        specializations.add(specialization);
        specialization.setDoctors(Set.of(doctor));
        List<DoctorDTO> doctors = new ArrayList<>();
        doctors.add(doctorDTO);

        when(mockSpecializationRepo.findAllBySpecializationType(any(SpecializationEnum.class))).thenReturn(specializations);
        when(mockDoctorMapper.mapToDto(doctor)).thenReturn(doctorDTO);
        List<DoctorDTO> expected = doctorService.getDoctorBySpecialization(specialization.getSpecializationType().getSpecialization());

        assertThat(expected).isEqualTo(doctors);
        verify(mockSpecializationRepo).findAllBySpecializationType(any(SpecializationEnum.class));
    }
}