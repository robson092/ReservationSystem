package com.example.reservation.repository;

import com.example.reservation.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAll();
    Page<Patient> findAll(Pageable page);

    Optional<Patient> findById(long id);

    Patient save(Patient patient);

    boolean existsById(long id);

    Optional<Patient> deleteById(int id);


}
