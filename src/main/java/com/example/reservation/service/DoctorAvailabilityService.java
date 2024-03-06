package com.example.reservation.service;

import com.example.reservation.dto.DoctorAvailabilityDTO;
import com.example.reservation.model.DoctorAvailability;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DoctorAvailabilityService {

    DoctorAvailabilityDTO saveAvailability(DoctorAvailabilityDTO doctorAvailability);
    Optional<DoctorAvailability> getAvailability(int id);
    List<DoctorAvailability> getAllAvailabilities();
    List<DoctorAvailability> getAvailAbilitiesByDayOfWeek(String dayOfWeek);
    List<DoctorAvailability> getAvailAbilitiesByDoctor(int id);
    void updateHours(int id, String startTime, String endTime);
    DoctorAvailability updateDays(int id, String dayOfWeek);
    void changeAvailableStatus(int id, boolean isAvailable);
    void deleteAvailability(int id);
}

