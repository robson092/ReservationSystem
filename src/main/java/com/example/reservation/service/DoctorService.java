package com.example.reservation.service;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.model.Doctor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Optional<DoctorDTO> getDoctor(Integer id);
    List<DoctorDTO> getAllDoctor();
    List<DoctorDTO> getAllDoctorsWithPage(Pageable page);
    DoctorDTO save(DoctorDTO doctorDto);
    Optional<Doctor> deleteDoctor(int id) throws CannotDeleteException;
    boolean isDoctorExist(Integer id);
    List<DoctorDTO> getDoctorBySpecialization(String specialization);


}
