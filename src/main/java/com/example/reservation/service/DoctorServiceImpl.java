package com.example.reservation.service;

import com.example.reservation.dto.AppointmentFromDoctorPovDTO;
import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.DoctorUpdateDTO;
import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.DoctorMapper;
import com.example.reservation.model.Doctor;
import com.example.reservation.model.HospitalAffiliation;
import com.example.reservation.model.Specialization;
import com.example.reservation.repository.DoctorRepository;
import com.example.reservation.repository.HospitalAffiliationRepository;
import com.example.reservation.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final HospitalAffiliationRepository hospitalAffiliationRepository;
    private final DoctorMapper mapper;
    private final ModelMapper modelMapper;

    @Override
    public Optional<DoctorDTO> getDoctor(Integer id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor not found");
        }
        return doctorRepository.findById(id)
                .map(DoctorDTO::new);
    }

    @Override
    public List<DoctorDTO> getAllDoctor() {
        return doctorRepository.findAll().stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> getAllDoctorsWithPage(Pageable page) {
        return doctorRepository.findAll(page).getContent().stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO save(DoctorDTO doctorDto) {
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        Set<Specialization> specializations = setSpecializationIfAlreadyExists(doctor);
        if(!specializations.contains(null)) {
            doctor.setSpecializations(specializations);
        }
        Set<HospitalAffiliation> hospitalAffiliations = setHospitalAffiliationIfAlreadyExists(doctor);
        if (!hospitalAffiliations.contains(null)) {
            doctor.setHospitalAffiliations(hospitalAffiliations);
        }
        Doctor savedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(savedDoctor, DoctorDTO.class);
    }

    @Override
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

    @Override
    public boolean isDoctorExist(Integer id) {
        return doctorRepository.existsById(id);
    }

    public void updateDoctor(Integer id, DoctorUpdateDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));
        mapper.updateDoctorFromDto(doctorDTO, doctor);
        doctorRepository.save(doctor);
    }

    @Override
    public List<DoctorDTO> getDoctorBySpecialization(String specialization) {
        SpecializationEnum specializationEnum = SpecializationEnum.valueOf(specialization.toUpperCase());
        List<Specialization> specializations = specializationRepository.findAllBySpecializationType(specializationEnum);
        return specializations.stream()
                .map(Specialization::getDoctors)
                .flatMap(doctors -> doctors
                        .stream()
                        .map(DoctorDTO::new))
                .collect(Collectors.toList());
    }

    private Set<Specialization> setSpecializationIfAlreadyExists(Doctor doctor) {
        Set<Specialization> requestedSpecializations = doctor.getSpecializations();
        return requestedSpecializations.stream()
                .map(specialization -> specializationRepository
                        .findBySpecializationType(specialization.getSpecializationType())
                        .orElse(null))
                .collect(Collectors.toSet());
    }

    private Set<HospitalAffiliation> setHospitalAffiliationIfAlreadyExists(Doctor doctor) {
        Set<HospitalAffiliation> requestedHospitalAffiliations = doctor.getHospitalAffiliations();
        return requestedHospitalAffiliations.stream()
                .map(hospitalAffiliation -> hospitalAffiliationRepository.findByHospitalNameAndCity(
                        hospitalAffiliation.getHospitalName(), hospitalAffiliation.getCity())
                        .orElse(null))
                .collect(Collectors.toSet());

    }

}
