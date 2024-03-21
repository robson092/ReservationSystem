package com.example.reservation.service;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.exception_handler.CannotDeleteException;
import com.example.reservation.mapper.AppointmentMapper;
import com.example.reservation.model.Appointment;
import com.example.reservation.repository.AppointmentRepository;
import com.example.reservation.repository.DoctorRepository;
import com.example.reservation.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    AppointmentMapper mockAppointmentMapper;

    @Mock
    AppointmentRepository mockAppointmentRepository;

    @InjectMocks
    AppointmentServiceImpl service;

    Appointment appointment;
    AppointmentDTO appointmentDTO;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointmentDTO = new AppointmentDTO();
    }

    @Test
    @DisplayName("Should return appointment when getAppointment invoke")
    void when_getAppointment_with_correct_id_then_return_appointment() {
        appointment.setId(1);
        appointmentDTO.setId(1);

        when(mockAppointmentRepository.findById(1)).thenReturn(Optional.ofNullable(appointment));
        when(mockAppointmentMapper.mapToDto(appointment)).thenReturn(appointmentDTO);
        AppointmentDTO expected = service.getAppointment(1).get();

        assertThat(expected).isSameAs(appointmentDTO);
        verify(mockAppointmentRepository).findById(1);
    }

    @Test
    @DisplayName("Throw illegalArgumentException when id is incorrect")
    void when_getAppointment_with_incorrect_id_then_throw_illegalArgumentException() {
        when(mockAppointmentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getAppointment(1));
    }

    @Test
    @DisplayName("Should return all appointments when getAll method invoke")
    void when_getAllAppointments_then_return_list_with_all_appointments() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        appointmentDTOs.add(appointmentDTO);

        when(mockAppointmentRepository.findAll()).thenReturn(appointments);
        when(mockAppointmentMapper.mapToDto(appointment)).thenReturn(appointmentDTO);
        List<AppointmentDTO> expected = service.getAllAppointments();

        assertEquals(expected, appointmentDTOs);
        verify(mockAppointmentRepository).findAll();
    }

    @Test
    @DisplayName("Should return all appointments based on specific page size")
    void when_getAllAppointments_then_return_list_of_appointments_based_on_page_size() {
        List<Appointment> appointments = new ArrayList<>();
        Page<Appointment> pageableAppointments = new PageImpl<>(appointments);
        Pageable pageable = mock(Pageable.class);

        when(mockAppointmentRepository.findAll(pageable)).thenReturn(pageableAppointments);
        service.getAllAppointmentsWithPage(pageable);

        verify(mockAppointmentRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should return all done appointments when pass true as param")
    void when_getDoneAppointments_with_true_then_return_list_of_done_appointments() {
        List<Appointment> doneAppointments = new ArrayList<>();
        List<AppointmentDTO> doneAppointmentsDTO = new ArrayList<>();
        appointment.setDone(true);
        appointmentDTO.setDone(true);
        doneAppointments.add(appointment);
        doneAppointmentsDTO.add(appointmentDTO);

        when(mockAppointmentRepository.findByDone(true)).thenReturn(doneAppointments);
        when(mockAppointmentMapper.mapToDto(appointment)).thenReturn(appointmentDTO);

        List<AppointmentDTO> expected = service.getDoneAppointments(true);
        assertThat(expected).isEqualTo(doneAppointmentsDTO);
        verify(mockAppointmentRepository).findByDone(true);
    }

    @Test
    @DisplayName("Should return empty list when pass true as param and there is no done appointments")
    void when_getDoneAppointments_with_true_and_no_done_appointment_then_return_empty_list() {
        List<Appointment> emptyListOfAppointments = Collections.emptyList();
        List<AppointmentDTO> emptyListOfAppointmentsDTO = Collections.emptyList();

        when(mockAppointmentRepository.findByDone(true)).thenReturn(emptyListOfAppointments);

        List<AppointmentDTO> expected = service.getDoneAppointments(true);
        assertThat(expected).isEqualTo(emptyListOfAppointmentsDTO);
        verify(mockAppointmentRepository).findByDone(true);

    }

    @Test
    @DisplayName("Should return appointment when save invoke")
    void when_save_then_return_appointment() {
        appointmentDTO.setId(1);
        appointment.setId(1);
        when(mockAppointmentRepository.save(appointment)).thenAnswer(i -> i.getArguments()[0]);
        when(mockAppointmentMapper.mapToDto(appointment)).thenReturn(appointmentDTO);
        when(mockAppointmentMapper.mapToEntity(appointmentDTO)).thenReturn(appointment);

        AppointmentDTO expected = service.save(appointmentDTO);

        assertThat(expected).isSameAs(appointmentDTO);
        verify(mockAppointmentRepository).save(appointment);
    }

    @Test
    @DisplayName("Should delete appointment if id is correct")
    void when_deleteAppointment_with_correct_id_then_delete() throws CannotDeleteException {
        appointment.setId(1);

        when(mockAppointmentRepository.existsById(appointment.getId())).thenReturn(true);
        service.deleteAppointment(appointment.getId());

        verify(mockAppointmentRepository).deleteById(appointment.getId());
    }

    @Test
    @DisplayName("Should throw illegalArgumentException when incorrect id provided")
    void when_deleteAppointment_with_incorrect_id_then_throw_illegalArgumentException() {
        when(mockAppointmentRepository.existsById(1)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.deleteAppointment(1));
        verify(mockAppointmentRepository).existsById(1);
    }

    @Test
    @DisplayName("Should make appointment done when invoke with correct id")
    void when_setAppointmentDone_with_correct_id_then_make_appointment_status_as_done() {
        appointment.setId(1);
        appointment.setDone(true);
        appointmentDTO.setId(1);
        appointmentDTO.setDone(true);
        when(mockAppointmentRepository.findById(1)).thenReturn(Optional.ofNullable(appointment));
        when(mockAppointmentMapper.mapToDto(appointment)).thenReturn(appointmentDTO);

        AppointmentDTO expected = service.setAppointmentDone(appointment.getId());

        assertThat(expected).isEqualTo(appointmentDTO);
        verify(mockAppointmentRepository).findById(1);
        verify(mockAppointmentRepository).save(appointment);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when invoke with inncorect id")
    void when_setAppointmentDone_with_incorrect_id_then_throw_IllegalArgumentException() {
        when(mockAppointmentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.setAppointmentDone(1));
        verify(mockAppointmentRepository).findById(1);
    }
}