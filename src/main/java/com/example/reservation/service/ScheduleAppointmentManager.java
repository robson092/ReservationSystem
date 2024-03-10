package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ScheduleAppointmentManager {

    List<AppointmentSlot> getAppointmentSlotsForDoctor(int doctorId);
    boolean checkIfAppointmentSlotIsFree(LocalDateTime appointmentDate, int doctorId);
    List<AppointmentSlot> getAllAvailableAppointmentSlotsByDoctorId(int doctorId);
    List<AppointmentSlot> getAllUpcomingAvailableAppointmentSlots();
}
