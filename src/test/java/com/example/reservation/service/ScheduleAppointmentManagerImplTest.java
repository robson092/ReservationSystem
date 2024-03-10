package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.repository.DoctorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleAppointmentManagerImplTest {

    @Mock
    DoctorRepository doctorRepository;
    @Mock
    AppointmentSlotService appointmentSlotService;

    @InjectMocks
    ScheduleAppointmentManagerImpl scheduleAppointmentManager;

    @Test
    @DisplayName("Should return all appointment slots for doctor if doctorId is correct")
    void when_getAppointmentSlotsForDoctor_with_correct_id_then_return_list_of_appointmentSlot() {
        Doctor doctor = new Doctor();
        DoctorAvailability doctorAvailability = new DoctorAvailability();
        List<DoctorAvailability> doctorAvailabilities = new ArrayList<>();
        doctorAvailabilities.add(doctorAvailability);
        doctor.setAvailability(doctorAvailabilities);
        Map<String, List<LocalDateTime>> hospitalsWithDates = new HashMap<>();
        List<AppointmentSlot> appointmentSlots = List.of(new AppointmentSlot());

        when(doctorRepository.findById(anyInt())).thenReturn(Optional.of(doctor));
        when(appointmentSlotService.getLocalDateTimesAndHospitalNamesFromDoctorAvailability(doctorAvailability)).thenReturn(hospitalsWithDates);
        when(appointmentSlotService.createAppointmentSlotsFromLocalDateTimesAndHospitalNames(hospitalsWithDates)).thenReturn(appointmentSlots);
        List<AppointmentSlot> expected = scheduleAppointmentManager.getAppointmentSlotsForDoctor(1);

        assertThat(expected).isEqualTo(appointmentSlots);
        verify(doctorRepository).findById(1);
    }

    @Test
    void checkIfAppointmentSlotIsFree() {
    }

    @Test
    void getAllAvailableAppointmentSlotsByDoctorId() {
    }

    @Test
    void getAllUpcomingAvailableAppointmentSlots() {
    }
}