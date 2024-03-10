package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.DoctorAvailability;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService {
    @Override
    public Map<String, List<LocalDateTime>> getLocalDateTimesAndHospitalNamesFromDoctorAvailability(DoctorAvailability doctorAvailability) {
        String hospitalName = doctorAvailability.getHospitalAffiliation().getHospitalName();
        int timeSlotPerClientInMinute = doctorAvailability.getHospitalAffiliation().getTimeSlotPerClientInMinute();
        List<LocalDateTime> localDateTimeFromStartTimeAndEndTime = getLocalDateTimeFromStartTimeAndEndTime(doctorAvailability, timeSlotPerClientInMinute);
        Map<String, List<LocalDateTime>> hospitalsWithAppointmentSlots = new HashMap<>();
        hospitalsWithAppointmentSlots.put(hospitalName, localDateTimeFromStartTimeAndEndTime);
        return hospitalsWithAppointmentSlots;
    }

    @Override
    public List<LocalDateTime> getLocalDateTimeFromStartTimeAndEndTime(DoctorAvailability doctorAvailability, int timeSlotPerClientInMinute) {
        List<LocalDateTime> appointmentSlots = new ArrayList<>();
        LocalTime startTime = doctorAvailability.getStartTime();
        while (!startTime.equals(doctorAvailability.getEndTime())) {
            appointmentSlots.add(LocalDateTime.of(doctorAvailability.getDate(), startTime));
            startTime = startTime.plusMinutes(timeSlotPerClientInMinute);
        }
        return appointmentSlots;
    }

    @Override
    public List<AppointmentSlot> createAppointmentSlotsFromLocalDateTimesAndHospitalNames(Map<String, List<LocalDateTime>> appointmentDatesPerHospital) {
        List<AppointmentSlot> appointmentSlotsPerHospital = new ArrayList<>();
        for (Map.Entry<String, List<LocalDateTime>> entry : appointmentDatesPerHospital.entrySet()) {
            List<LocalDateTime> slots = entry.getValue();
            for (LocalDateTime slot : slots) {
                AppointmentSlot appointmentSlot = AppointmentSlot.builder()
                        .hospital(entry.getKey())
                        .dateTime(slot)
                        .dayOfWeek(slot.getDayOfWeek())
                        .build();
                appointmentSlotsPerHospital.add(appointmentSlot);
            }
        }
        return appointmentSlotsPerHospital;
    }
}
