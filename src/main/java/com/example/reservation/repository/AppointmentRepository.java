package com.example.reservation.repository;

import com.example.reservation.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> deleteById(int id);
}
