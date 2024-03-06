package com.example.reservation.repository;

import com.example.reservation.model.Doctor;
import com.example.reservation.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Integer> {

    List<DoctorAvailability> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<DoctorAvailability> findByDoctor(Doctor doctor);

}
