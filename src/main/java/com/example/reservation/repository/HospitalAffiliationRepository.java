package com.example.reservation.repository;

import com.example.reservation.model.HospitalAffiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalAffiliationRepository extends JpaRepository<HospitalAffiliation, Integer> {
}
