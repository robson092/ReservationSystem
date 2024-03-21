package com.example.reservation.service;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.HospitalAffiliationDTO;
import com.example.reservation.dto.PatientDTO;
import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.model.ScheduleAppointmentTemplate;
import com.example.reservation.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleAppointmentManagerImplTest {

    @Mock
    AppointmentServiceImpl appointmentService;
    @Mock
    AppointmentSlotServiceImpl appointmentSlotService;

    @InjectMocks
    ScheduleAppointmentManagerImpl scheduleAppointmentManager;

    ScheduleAppointmentTemplate appointmentTemplate;
    AppointmentDTO appointmentDTO;
    AppointmentSlot appointmentSlot;

    @BeforeEach
    void setUp() {
        PatientDTO patientDTO = new PatientDTO();
        DoctorDTO doctorDTO = new DoctorDTO();
        HospitalAffiliationDTO hospitalAffiliationDTO = new HospitalAffiliationDTO();
        hospitalAffiliationDTO.setHospitalName("Hospital");
        appointmentDTO = new AppointmentDTO();
        appointmentSlot = new AppointmentSlot();
        appointmentSlot.setHospital(hospitalAffiliationDTO.getHospitalName());
        appointmentSlot.setDateTime(LocalDateTime.MAX);
        appointmentSlot.setHospital("Hospital");
        appointmentTemplate = new ScheduleAppointmentTemplate();
        appointmentTemplate.setPatientDTO(patientDTO);
        appointmentTemplate.setDoctorDTO(doctorDTO);
        appointmentTemplate.setHospitalAffiliationDTO(hospitalAffiliationDTO);
        appointmentTemplate.setAppointmentDateTime(LocalDateTime.MAX);
        appointmentDTO.setDoctor(doctorDTO);
        appointmentDTO.setPatient(patientDTO);
        appointmentDTO.setHospital(hospitalAffiliationDTO);
        appointmentDTO.setDate(LocalDateTime.MAX);
    }

    @Test
    @DisplayName("Should return appointmentDTO when invoke")
    void when_schedule_with_appointment_template_then_return_appointmentDTO() {
        List<AppointmentSlot> appointmentSlots = List.of(appointmentSlot);
        appointmentTemplate.setAppointmentDateTime(LocalDateTime.MAX);

        when(appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(anyInt())).thenReturn(appointmentSlots);
        when(appointmentService.save(any(AppointmentDTO.class))).then(AdditionalAnswers.returnsFirstArg());

        AppointmentDTO actual = scheduleAppointmentManager.schedule(appointmentTemplate);

        assertThat(actual).isEqualTo(appointmentDTO);
        verify(appointmentService).save(appointmentDTO);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when proposed time is not free")
    void when_schedule_with_appointment_template_contains_not_free_appointment_date_time_then_throw_IllegalArgumentException() {
        List<AppointmentSlot> appointmentSlots = List.of(appointmentSlot);
        appointmentTemplate.setAppointmentDateTime(LocalDateTime.MIN);

        when(appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(anyInt())).thenReturn(appointmentSlots);

        assertThrows(IllegalArgumentException.class, () -> scheduleAppointmentManager.schedule(appointmentTemplate));
    }
}