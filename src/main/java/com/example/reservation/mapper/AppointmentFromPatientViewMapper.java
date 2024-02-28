package com.example.reservation.mapper;

import com.example.reservation.dto.AppointmentFromPatientViewDTO;
import com.example.reservation.model.Appointment;
import com.example.reservation.model.Patient;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AppointmentFromPatientViewMapper {

    public AppointmentFromPatientViewDTO mapToAppointmentDtoFromPatientView(Appointment appointment) {
        return AppointmentFromPatientViewDTO.builder()
                .appointmentId(appointment.getId())
                .date(appointment.getDate())
                .doctorId(appointment.getPatient().getId())
                .doctorName(appointment.getPatient().getName())
                .doctorSurname(appointment.getPatient().getSurname())
                .build();
    }

    public Set<AppointmentFromPatientViewDTO> getAppointmentsFromPatientViewDTOs(Patient patient) {
        return patient.getAppointments().stream()
                .map(this::mapToAppointmentDtoFromPatientView)
                .collect(Collectors.toSet());
    }
}
