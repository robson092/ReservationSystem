package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleAppointmentManagerImpl implements ScheduleAppointmentManager {

    private final DoctorRepository doctorRepository;
    private final AppointmentSlotService appointmentSlotService;
    @Override
    public List<AppointmentSlot> getAppointmentSlotsForDoctor(int doctorId) {
//        Doctor doctor = doctorRepository.findById(doctorId)
//                .orElseThrow(() -> new IllegalArgumentException("Not found"));
//        List<DoctorAvailability> doctorAvailabilities = doctor.getAvailability();
//        List<AppointmentSlot> appointmentSlotsPerHospital = new ArrayList<>();
//        for (DoctorAvailability doctorAvailability : doctorAvailabilities) {
//            Map<String, List<LocalDateTime>> appointmentDatesPerHospital = appointmentSlotService.getLocalDateTimesAndHospitalNamesFromDoctorAvailability(doctorAvailability);
//            appointmentSlotsPerHospital = appointmentSlotService.createAppointmentSlotsFromLocalDateTimesAndHospitalNames(appointmentDatesPerHospital);
//        }
        return Collections.emptyList();
    }

    @Override
    public boolean checkIfAppointmentSlotIsFree(LocalDateTime appointmentDate, int doctorId) {
        List<AppointmentSlot> appointmentSlots = getAppointmentSlotsForDoctor(doctorId);
        List<LocalDateTime> dateTimes = appointmentSlots.stream()
                .map(AppointmentSlot::getDateTime)
                .collect(Collectors.toList());
        return dateTimes.contains(appointmentDate);
    }

    @Override
    public List<AppointmentSlot> getAllAvailableAppointmentSlotsByDoctorId(int id) {
        List<AppointmentSlot> appointmentSlotsForDoctor = getAppointmentSlotsForDoctor(id);
        return appointmentSlotsForDoctor.stream()
                .filter(appointmentSlot -> checkIfAppointmentSlotIsFree(appointmentSlot.getDateTime(), id))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentSlot> getAllUpcomingAvailableAppointmentSlots() {
        return null;
    }
}
