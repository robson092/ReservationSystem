package com.example.reservation.service;

import com.example.reservation.dto.AppointmentFromDoctorPovDTO;
import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.DoctorUpdateDTO;
import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.DoctorMapper;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.Specialization;
import com.example.reservation.repository.DoctorRepository;
import com.example.reservation.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final DoctorMapper mapper;

    public Optional<DoctorDTO> getDoctor(Integer id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor not found");
        }
        return doctorRepository.findById(id)
                .map(DoctorDTO::new);
    }

    public List<DoctorDTO> getAllDoctor() {
        return doctorRepository.findAll().stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getAllDoctorsWithPage(Pageable page) {
        return doctorRepository.findAll(page).getContent().stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public Doctor save(Doctor doctor) {
        Set<Specialization> requestedSpecializations = doctor.getSpecializations();
        Set<Specialization> specializations = requestedSpecializations.stream()
                .map(specialization -> specializationRepository
                        .findBySpecializationType(specialization.getSpecializationType())
                        .orElse(null))
                .collect(Collectors.toSet());
        if (!specializations.contains(null)) {
            doctor.setSpecializations(specializations);
        }
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> deleteDoctor(int id) throws CannotDeleteException {
        if (!isDoctorExist(id)) {
            throw new IllegalArgumentException("Doctor not found");
        }
        DoctorDTO doctor = getDoctor(id).orElse(null);
        Set<AppointmentFromDoctorPovDTO> appointments = Optional.ofNullable(doctor.getAppointments())
                .orElseGet(Collections::emptySet);
        if (!appointments.isEmpty()) {
            List<Integer> ids = appointments.stream()
                    .map(AppointmentFromDoctorPovDTO::getAppointmentId)
                    .collect(Collectors.toList());
            throw new CannotDeleteException("Cannot delete Doctor due to appointment scheduled. Appointments id: " + ids);
        }
        return doctorRepository.deleteById(id);
    }

    public boolean isDoctorExist(Integer id) {
        return doctorRepository.existsById(id);
    }

    public void updateDoctor(Integer id, DoctorUpdateDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));
        mapper.updateDoctorFromDto(doctorDTO, doctor);
        doctorRepository.save(doctor);
    }

    public List<DoctorDTO> findBySpecializations(String specialization) {
        SpecializationEnum specializationEnum = SpecializationEnum.valueOf(specialization.toUpperCase());
        List<Specialization> specializations = specializationRepository.findAllBySpecializationType(specializationEnum);
        return specializations.stream()
                .map(Specialization::getDoctors)
                .flatMap(doctors -> doctors
                        .stream()
                        .map(DoctorDTO::new))
                .collect(Collectors.toList());
    }

}
