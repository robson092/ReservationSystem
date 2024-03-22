package com.example.reservation.repository;

import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalAffiliationRepository extends JpaRepository<HospitalAffiliation, Integer> {

    Optional<HospitalAffiliation> findByHospitalNameAndCity(String hospitalName, String city);
    List<HospitalAffiliation> findByCity(String city);
    List<HospitalAffiliation> findByDoctors(Doctor doctor);
}
