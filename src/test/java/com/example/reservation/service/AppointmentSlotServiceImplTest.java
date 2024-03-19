package com.example.reservation.service;

import com.example.reservation.model.*;
import com.example.reservation.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentSlotServiceImplTest {

    @Mock
    DoctorRepository mockDoctorRepository;
    @InjectMocks
    AppointmentSlotServiceImpl appointmentSlotService;
    Doctor doctor;
    DoctorAvailability doctorAvailability;
    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctorAvailability = new DoctorAvailability();
        doctorAvailability.setDate(LocalDate.MAX);
        doctorAvailability.setStartTime(LocalTime.MAX.minusMinutes(100));
        doctorAvailability.setEndTime(LocalTime.MAX);
        HospitalAffiliation hospitalAffiliation = new HospitalAffiliation();
        hospitalAffiliation.setHospitalName("Hospital");
        hospitalAffiliation.setTimeSlotPerClientInMinute(10);
        doctorAvailability.setHospitalAffiliation(hospitalAffiliation);
        doctor.setAvailability(List.of(doctorAvailability));
    }

    @Test
    @DisplayName("Should return list of all AppointmentSlots objects when pass with correct doctor id")
    void when_getAllFreeAppointmentSlotsForDoctor_with_correct_doctor_id_then_return_list_of_appointmentSlots_objects() {
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        List<AppointmentSlot> appointmentSlots = new ArrayList<>();
        appointmentSlots.add(appointmentSlot);
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDateTime.now().plusMinutes(20));
        doctor.setAppointments(Set.of(appointment));

        when(mockDoctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));

        List<AppointmentSlot> expected = appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(1);
        assertThat(expected).hasSameClassAs(appointmentSlots);
        verify(mockDoctorRepository, times(2)).findById(1);
    }

    @Test
    @DisplayName("Should return empty list when another appointment date is the same as appointmentSlot date")
    void when_getAllFreeAppointmentSlotsForDoctor_with_appointment_date_the_same_as_appointment_slot_date_then_return_empty_list() {
        Appointment appointment = new Appointment();
        int timeSlotPerClientInMinute = doctorAvailability.getHospitalAffiliation().getTimeSlotPerClientInMinute();
        appointment.setDate(LocalDateTime.MAX.minusMinutes(timeSlotPerClientInMinute));
        doctor.setAppointments(Set.of(appointment));

        when(mockDoctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));

        List<AppointmentSlot> actual = appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(1);
        assertEquals(9, actual.size());
        verify(mockDoctorRepository, times(2)).findById(1);
    }

    @Test
    @DisplayName("Should throw exception when pass with incorrect doctor id")
    void when_getAllAppointmentSlotsForDoctor_with_incorrect_doctor_id_then_throw_exception() {
        when(mockDoctorRepository.findById(anyInt())).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(1));
        verify(mockDoctorRepository).findById(1);
    }

    @Test
    @DisplayName("Should return list of AppointmentSlots with given hospital when pass with correct doctor id")
    void when_getAllAppointmentSlotsForDoctorByHospital_with_correct_doctor_id_then_return_list_of_appointmentSlots_of_given_hospital() {
        Appointment appointment = new Appointment();
        doctor.setAppointments(Set.of(appointment));
        int timeSlotPerClientInMinute = doctorAvailability.getHospitalAffiliation().getTimeSlotPerClientInMinute();
        appointment.setDate(LocalDateTime.MAX.minusMinutes(timeSlotPerClientInMinute));

        when(mockDoctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));

        List<AppointmentSlot> expected = appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(1);
        assertThat(expected.get(0).getHospital()).isEqualTo("Hospital");
        verify(mockDoctorRepository, times(2)).findById(1);
    }

    @Test
    @DisplayName("Should return list of AppointmentSlots with given date when pass with correct doctor id")
    void when_getAllAppointmentSlotsForDoctorByHospital_with_correct_doctor_id_then_return_list_of_appointmentSlots_of_given_date() {
        Appointment appointment = new Appointment();
        doctor.setAppointments(Set.of(appointment));
        int timeSlotPerClientInMinute = doctorAvailability.getHospitalAffiliation().getTimeSlotPerClientInMinute();
        appointment.setDate(LocalDateTime.MAX.minusMinutes(timeSlotPerClientInMinute));

        when(mockDoctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));

        List<AppointmentSlot> expected = appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(1);
        assertThat(expected.get(0).getDateTime().toLocalDate()).isEqualTo(doctorAvailability.getDate());
        verify(mockDoctorRepository, times(2)).findById(1);
    }
}