package com.example.reservation.service;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.PatientDTO;
import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.ScheduleAppointmentTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ScheduleAppointmentManager {

    AppointmentDTO schedule(ScheduleAppointmentTemplate appointmentTemplate);
}
