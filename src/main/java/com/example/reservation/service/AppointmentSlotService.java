package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.DoctorAvailability;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AppointmentSlotService {

    Map<String, List<LocalDateTime>> getLocalDateTimesAndHospitalNamesFromDoctorAvailability(DoctorAvailability doctorAvailability);
    List<AppointmentSlot> createAppointmentSlotsFromLocalDateTimesAndHospitalNames(Map<String, List<LocalDateTime>> appointmentDatesPerHospital);
    List<LocalDateTime> getLocalDateTimeFromStartTimeAndEndTime(DoctorAvailability doctorAvailability, int timeSlotPerClientInMinute);
}
