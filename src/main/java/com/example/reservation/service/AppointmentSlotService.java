package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;

import java.util.List;

public interface AppointmentSlotService {
    List<AppointmentSlot> getAllAppointmentSlotsForDoctor(Integer id);
    List<AppointmentSlot> getAllAppointmentSlotsForDoctorByHospital(Integer id, String hospital);
    List<AppointmentSlot> getAllAppointmentSlotsForDoctorByDate(Integer id, String date);
}
