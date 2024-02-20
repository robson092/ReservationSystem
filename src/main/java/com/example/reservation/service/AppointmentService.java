package com.example.reservation.service;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.model.Appointment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Optional<AppointmentDTO> getAppointment(Integer id);
    List<AppointmentDTO> getAllAppointments();
    List<AppointmentDTO> getAllAppointmentsWithPage(Pageable page);
    List<AppointmentDTO> getDoneAppointments(boolean done);
    AppointmentDTO save(AppointmentDTO appointmentDTO);
    Optional<Appointment> deleteAppointment(int id);
    boolean isAppointmentExist(Integer id);
    void setAppointmentDone(int id);
}
