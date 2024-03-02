package com.example.reservation.repository;

import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByHospitalAffiliations(HospitalAffiliation hospitalAffiliation);

}
