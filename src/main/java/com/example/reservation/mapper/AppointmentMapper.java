package com.example.reservation.mapper;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;

    public AppointmentDTO mapToDto(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .patientDTO(patientMapper.mapToDto(appointment.getPatient()))
                .doctorDTO(doctorMapper.mapToDto(appointment.getDoctor()))
                .doctorSpecialization(appointment.getDoctor().getSpecializations())
                .done(appointment.isDone())
                .build();
    }

    public Appointment mapToEntity(AppointmentDTO dto) {
        return Appointment.builder()
                .date(dto.getDate())
                .patient(patientMapper.mapToEntity(dto.getPatientDTO()))
                .doctor(doctorMapper.mapToEntity(dto.getDoctorDTO()))
                .build();
    }
}
