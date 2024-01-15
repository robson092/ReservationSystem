package com.example.reservation.repository;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    List<Specialization> findBySpecializationType(SpecializationEnum specializationType);
}
