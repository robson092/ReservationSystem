package com.example.reservation.service;

import com.example.reservation.dto.DoctorAvailabilityDTO;
import com.example.reservation.model.DoctorAvailability;

import java.util.List;
import java.util.Optional;

public interface DoctorAvailabilityService {

    DoctorAvailabilityDTO saveAvailability(DoctorAvailabilityDTO doctorAvailability);
    Optional<DoctorAvailability> getAvailability(int id);
    List<DoctorAvailabilityDTO> getAllAvailabilities();
    List<DoctorAvailabilityDTO> getAvailAbilitiesByDate(String dayOfWeek);
    List<DoctorAvailabilityDTO> getAvailAbilitiesByDoctor(int id);
    void updateHours(int id, String startTime, String endTime);
    void updateDate(int id, String dayOfWeek);
    void changeAvailableStatus(int id, boolean isAvailable);
    void deleteAvailability(int id);
}

