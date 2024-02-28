package com.example.reservation.mapper;

import com.example.reservation.dto.AppointmentFromDoctorViewDTO;
import com.example.reservation.model.Appointment;
import com.example.reservation.model.Doctor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AppointmentFromDoctorViewMapper {

    public AppointmentFromDoctorViewDTO mapToAppointmentDtoFromDoctorView(Appointment appointment) {
        return AppointmentFromDoctorViewDTO.builder()
                .appointmentId(appointment.getId())
                .date(appointment.getDate())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getName())
                .patientSurname(appointment.getPatient().getSurname())
                .build();
    }

    public Set<AppointmentFromDoctorViewDTO> getAppointmentsFromDoctorViewDTOs(Doctor doctor) {
        return doctor.getAppointments().stream()
                .map(this::mapToAppointmentDtoFromDoctorView)
                .collect(Collectors.toSet());
    }
}
