package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.model.HospitalAffiliation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentSlotServiceImplTest {

    AppointmentSlotServiceImpl appointmentSlotService;
    DoctorAvailability doctorAvailability;
    @BeforeEach
    void setUp() {
        appointmentSlotService = new AppointmentSlotServiceImpl();
        doctorAvailability = new DoctorAvailability();
    }


    @Test
    @DisplayName("Should return map with hospital names and dates when invoke on existing doctor availability")
    void when_invoke_with_existing_doctorAvailability_then_return_map_with_hospital_and_localDateTime() {
        HospitalAffiliation hospitalAffiliation = new HospitalAffiliation();
        hospitalAffiliation.setHospitalName("Hospital");
        doctorAvailability.setHospitalAffiliation(hospitalAffiliation);
        AppointmentSlotServiceImpl appointmentSlotService = new AppointmentSlotServiceImpl();
        var spyAppointmentSlotService = spy(appointmentSlotService);
        List<LocalDateTime> localDateTimes = new ArrayList<>();

        doReturn(localDateTimes).when(spyAppointmentSlotService).getLocalDateTimeFromStartTimeAndEndTime(any(DoctorAvailability.class), anyInt());

        Map<String, List<LocalDateTime>> expected = spyAppointmentSlotService.getLocalDateTimesAndHospitalNamesFromDoctorAvailability(doctorAvailability);
        assertTrue(expected.containsKey(hospitalAffiliation.getHospitalName()));
    }

    @Test
    @DisplayName("Should return list of AppointmentSlots objects when pass with map contains hospital name and localDateTimes")
    void when_invoke_with_hospitalNames_and_localDateTimes_map_then_return_list_of_appointmentSlots_objects() {
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.now());
        String hospitalName = "Hospital";
        Map<String, List<LocalDateTime>> hospitalsWithLocalDateTimes = new HashMap<>();
        hospitalsWithLocalDateTimes.put(hospitalName, localDateTimes);
        AppointmentSlot appointmentSlot = AppointmentSlot.builder()
                .hospital(hospitalName)
                .dateTime(localDateTimes.get(0))
                .dayOfWeek(localDateTimes.get(0).getDayOfWeek())
                .build();
        List<AppointmentSlot> appointmentSlots = List.of(appointmentSlot);

        List<AppointmentSlot> expected = appointmentSlotService.createAppointmentSlotsFromLocalDateTimesAndHospitalNames(hospitalsWithLocalDateTimes);

        assertThat(expected).isEqualTo(appointmentSlots);
    }

    @Test
    @DisplayName("Should return list of localDateTime when pass doctor availability")
    void when_invoke_with_existing_doctorAvailability_then_return_list_of_localDateTime() {
        int timeSlotPerClientInMinute = 10;
        doctorAvailability.setDate(LocalDate.now());
        doctorAvailability.setStartTime(LocalTime.now());
        doctorAvailability.setEndTime(LocalTime.now().plusMinutes(timeSlotPerClientInMinute * 6));

        List<LocalDateTime> expected = appointmentSlotService.getLocalDateTimeFromStartTimeAndEndTime(doctorAvailability, timeSlotPerClientInMinute);

        assertEquals(6, expected.size());
    }
}