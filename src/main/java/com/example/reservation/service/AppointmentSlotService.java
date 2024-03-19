package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;

import java.util.List;

public interface AppointmentSlotService {
    List<AppointmentSlot> getAllFreeAppointmentSlotsForDoctor(Integer id);
    List<AppointmentSlot> getAllFreeAppointmentSlotsForDoctorByHospital(Integer id, String hospital);
    List<AppointmentSlot> getAllFreeAppointmentSlotsForDoctorByDate(Integer id, String date);
}
