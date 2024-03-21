package com.example.reservation.repository;

import com.example.reservation.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDone(boolean done);
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1")
    List<Appointment> findByDoctor(Integer id);
}
