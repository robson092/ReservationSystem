package com.example.reservation.service;

import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AppointmentSlotServiceImpl implements AppointmentSlotService {
    private final DoctorRepository doctorRepository;

    @Override
    public List<AppointmentSlot> getAllAppointmentSlotsForDoctor(Integer id) {
        Map<String, List<LocalDateTime>> appointmentDatesPerHospital = getAllDoctorAvailabilityDaysAndHours(id);
        List<AppointmentSlot> appointmentSlots = new ArrayList<>();
        for (Map.Entry<String, List<LocalDateTime>> entry : appointmentDatesPerHospital.entrySet()) {
            List<LocalDateTime> slots = entry.getValue();
            for (LocalDateTime slot : slots) {
                AppointmentSlot appointmentSlot = AppointmentSlot.builder()
                        .hospital(entry.getKey())
                        .dateTime(slot)
                        .dayOfWeek(slot.getDayOfWeek())
                        .build();
                appointmentSlots.add(appointmentSlot);
            }
        }
        return appointmentSlots;
    }

    @Override
    public List<AppointmentSlot> getAllAppointmentSlotsForDoctorByHospital(Integer id, String hospital) {
        List<AppointmentSlot> allAppointmentSlotsForDoctor = getAllAppointmentSlotsForDoctor(id);
        return allAppointmentSlotsForDoctor.stream()
                .filter(appointmentSlot -> appointmentSlot.getHospital().equals(hospital))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentSlot> getAllAppointmentSlotsForDoctorByDate(Integer id, String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<AppointmentSlot> allAppointmentSlotsForDoctor = getAllAppointmentSlotsForDoctor(id);
        return allAppointmentSlotsForDoctor.stream()
                .filter(appointmentSlot -> appointmentSlot.getDateTime().toLocalDate().equals(parsedDate))
                .collect(Collectors.toList());
    }

    private Map<String, List<LocalDateTime>> getAllDoctorAvailabilityDaysAndHours(Integer id) {
        List<DoctorAvailability> availabilities = doctorRepository.findById(id)
                .orElseThrow()
                .getAvailability();
        Map<String, List<LocalDateTime>> slotsPerHospital = new HashMap<>();
        for (DoctorAvailability doctorAvailability : availabilities) {
            int timeSlotPerClientInMinute = doctorAvailability.getHospitalAffiliation().getTimeSlotPerClientInMinute();
            List<LocalDateTime> localDateTimeFromStartTimeAndEndTime = getLocalDateTimeFromStartTimeAndEndTime(doctorAvailability, timeSlotPerClientInMinute);
            String hospitalName = doctorAvailability.getHospitalAffiliation().getHospitalName();
            if (slotsPerHospital.containsKey(hospitalName)) {
                List<LocalDateTime> localDateTimes = slotsPerHospital.get(hospitalName);
                slotsPerHospital.put(hospitalName, concatLists(localDateTimes, localDateTimeFromStartTimeAndEndTime));
            } else {
                slotsPerHospital.put(hospitalName, localDateTimeFromStartTimeAndEndTime);
            }
        }
        return slotsPerHospital;
    }

    private List<LocalDateTime> getLocalDateTimeFromStartTimeAndEndTime(DoctorAvailability doctorAvailability, int timeSlotPerClientInMinute) {
        List<LocalDateTime> appointmentSlots = new ArrayList<>();
        LocalTime startTime = doctorAvailability.getStartTime();
        while (!startTime.equals(doctorAvailability.getEndTime())) {
            appointmentSlots.add(LocalDateTime.of(doctorAvailability.getDate(), startTime));
            startTime = startTime.plusMinutes(timeSlotPerClientInMinute);
        }
        return appointmentSlots;
    }

    private List<LocalDateTime> concatLists(List<LocalDateTime> first, List<LocalDateTime> second) {
        return Stream.of(first, second)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
