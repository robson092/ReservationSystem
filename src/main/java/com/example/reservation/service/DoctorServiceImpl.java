package com.example.reservation.service;

import com.example.reservation.dto.DoctorDTO;
import com.example.reservation.dto.DoctorUpdateDTO;
import com.example.reservation.enums.SpecializationEnum;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.DoctorMapper;
import com.example.reservation.mapper.DoctorUpdateMapper;
import com.example.reservation.model.*;
import com.example.reservation.repository.DoctorRepository;
import com.example.reservation.repository.HospitalAffiliationRepository;
import com.example.reservation.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
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
    private final DoctorUpdateMapper updateMapper;
    private final DoctorMapper mapper;

    @Override
    public Optional<DoctorDTO> getDoctor(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));
        return Optional.ofNullable(mapper.mapToDto(doctor));
    }

    @Override
    public List<DoctorDTO> getAllDoctor() {
        ArrayList<Doctor> doctors = new ArrayList<>(doctorRepository.findAll());
        return doctors.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> getAllDoctorsWithPage(Pageable page) {
        ArrayList<Doctor> doctors = new ArrayList<>(doctorRepository.findAll(page).getContent());
        return doctors.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO save(DoctorDTO doctorDto) {
        Doctor doctor = mapper.mapToEntity(doctorDto);
        Set<Specialization> specializations = setSpecializationIfAlreadyExists(doctor);
        if(!specializations.contains(null)) {
            doctor.setSpecializations(specializations);
        }
        Set<HospitalAffiliation> hospitalAffiliations = setHospitalAffiliationIfAlreadyExists(doctor);
        if (!hospitalAffiliations.contains(null)) {
            doctor.setHospitalAffiliations(hospitalAffiliations);
        }
        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapper.mapToDto(savedDoctor);
    }

    @Override
    public void deleteDoctor(int id) throws CannotDeleteException {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find doctor"));
        if (!allowToDeleteDoctor(doctor)) {
            List<Integer> doctorAppointmentsIDs = getDoctorAppointmentsIDs(doctor);
            throw new CannotDeleteException("Cannot delete Doctor due to appointment scheduled. Appointments id: " + doctorAppointmentsIDs);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public boolean isDoctorExist(Integer id) {
        return doctorRepository.existsById(id);
    }

    @Override
    public void updateDoctor(Integer id, DoctorUpdateDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));
        updateMapper.updateDoctorFromDto(doctorDTO, doctor);
        doctorRepository.save(doctor);
    }

    @Override
    public List<DoctorDTO> getAllDoctorsBySpecialization(String specialization) {
        SpecializationEnum specializationEnum = SpecializationEnum.valueOf(specialization.toUpperCase());
        List<Specialization> specializations = specializationRepository.findAllBySpecializationType(specializationEnum);
        return specializations.stream()
                .map(Specialization::getDoctors)
                .flatMap(doctors -> doctors.stream()
                        .map(mapper::mapToDto))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> getAllDoctorsByCityAndHospitalName(String city, String hospitalName) {
        List<DoctorDTO> doctorDTOs = new ArrayList<>();
        Optional<HospitalAffiliation> hospitalNameAndCity = hospitalAffiliationRepository.findByHospitalNameAndCity(hospitalName, city);
        HospitalAffiliation hospitalAffiliation = hospitalNameAndCity.orElse(null);
        if (hospitalAffiliation != null) {
            List<Doctor> doctors = doctorRepository.findByHospitalAffiliations(hospitalAffiliation);
            doctorDTOs = doctors.stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return doctorDTOs;
    }

    @Override
    public List<DoctorDTO> getAllDoctorsByCity(String city) {
        List<DoctorDTO> doctorDTOs = new ArrayList<>();
        List<HospitalAffiliation> hospitalsInRequestedCity = hospitalAffiliationRepository.findByCity(city);
        if (!hospitalsInRequestedCity.isEmpty()) {
            doctorDTOs = hospitalsInRequestedCity.stream()
                    .map(HospitalAffiliation::getDoctors)
                    .flatMap(doctors -> doctors.stream()
                            .map(mapper::mapToDto))
                    .collect(Collectors.toList());
        }
        return doctorDTOs;
    }

    private List<Integer> getDoctorAppointmentsIDs(Doctor doctor) {
        return doctor.getAppointments().stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());
    }

    private boolean allowToDeleteDoctor(Doctor doctor) {
        Set<Appointment> appointments = Optional.ofNullable(doctor.getAppointments())
                .orElseGet(Collections::emptySet);
        return appointments.isEmpty();
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
