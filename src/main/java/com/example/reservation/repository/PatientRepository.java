package com.example.reservation.repository;

import com.example.reservation.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> deleteById(int id);


}
