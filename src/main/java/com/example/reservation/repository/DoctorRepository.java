package com.example.reservation.repository;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findBySpecializations(SpecializationEnum specializationType);

}
