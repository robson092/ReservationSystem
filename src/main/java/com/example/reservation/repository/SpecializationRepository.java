package com.example.reservation.repository;

import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    List<Specialization> findAllBySpecializationType(SpecializationEnum specializationType);
    Optional<Specialization> findBySpecializationType(SpecializationEnum specializationType);
}
