package com.example.reservation.service;

import com.example.reservation.dto.DoctorAvailabilityDTO;
import com.example.reservation.mapper.DoctorAvailabilityMapper;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.DoctorAvailability;
import com.example.reservation.repository.DoctorAvailabilityRepository;
import com.example.reservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityMapper mapper;
    private static final String NOT_FOUND = "Not found";
    @Override
    public DoctorAvailabilityDTO saveAvailability(DoctorAvailabilityDTO doctorAvailabilityDTO) {
        DoctorAvailability doctorAvailability = mapper.mapToEntity(doctorAvailabilityDTO);
        DoctorAvailability savedAvailability = doctorAvailabilityRepository.save(doctorAvailability);
        return mapper.mapToDto(savedAvailability);
    }

    @Override
    public Optional<DoctorAvailability> getAvailability(int id) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        return Optional.of(doctorAvailability);
    }

    @Override
    public List<DoctorAvailability> getAvailAbilitiesByDayOfWeek(String dayOfWeek) {
        DayOfWeek day = DayOfWeek.valueOf(dayOfWeek);
        return doctorAvailabilityRepository.findByDayOfWeek(day);
    }

    @Override
    public List<DoctorAvailability> getAvailAbilitiesByDoctor(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
       return doctorAvailabilityRepository.findByDoctor(doctor);
    }

    @Override
    public List<DoctorAvailability> getAllAvailabilities() {
        return doctorAvailabilityRepository.findAll();
    }

    @Override
    public void updateHours(int id, String startTime, String endTime) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        doctorAvailability.setStartTime(formatStringToTime(startTime));
        doctorAvailability.setEndTime(formatStringToTime(endTime));
        doctorAvailabilityRepository.save(doctorAvailability);
    }

    @Override
    public DoctorAvailability updateDays(int id, String day) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
        doctorAvailability.setDayOfWeek(dayOfWeek);
        doctorAvailabilityRepository.save(doctorAvailability);
        return doctorAvailability;
    }

    @Override
    public void deleteAvailability(int id) {
        if (doctorAvailabilityRepository.existsById(id)) {
            doctorAvailabilityRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }

    @Override
    public void changeAvailableStatus(int id, boolean isAvailable) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        doctorAvailability.setAvailable(isAvailable);
        doctorAvailabilityRepository.save(doctorAvailability);
    }

    private LocalTime formatStringToTime(String time) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("h[:mm]");
        return LocalTime.parse(time, parser);
    }
}
