package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleAppointmentManagerImpl implements ScheduleAppointmentManager {

    private final DoctorRepository doctorRepository;
    private final AppointmentSlotService appointmentSlotService;
    @Override
    public List<AppointmentSlot> getAppointmentSlotsForDoctor(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        List<DoctorAvailability> doctorAvailabilities = doctor.getAvailability();
        List<AppointmentSlot> appointmentSlotsPerHospital = new ArrayList<>();
        for (DoctorAvailability doctorAvailability : doctorAvailabilities) {
            Map<String, List<LocalDateTime>> appointmentDatesPerHospital = appointmentSlotService.getLocalDateTimesAndHospitalNamesFromDoctorAvailability(doctorAvailability);
            appointmentSlotsPerHospital = appointmentSlotService.createAppointmentSlotsFromLocalDateTimesAndHospitalNames(appointmentDatesPerHospital);
        }
        return appointmentSlotsPerHospital;
    }

    @Override
    public boolean checkIfAppointmentSlotIsFree(LocalDateTime appointmentDate, int doctorId) {
        return false;
    }

    @Override
    public List<AppointmentSlot> getAllAvailableAppointmentSlotsByDoctorId(int id) {
        return null;
    }

    @Override
    public List<AppointmentSlot> getAllUpcomingAvailableAppointmentSlots() {
        return null;
    }
}
