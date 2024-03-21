package com.example.reservation.service;

import com.example.reservation.dto.AppointmentDTO;
import com.example.reservation.model.AppointmentSlot;
import com.example.reservation.model.ScheduleAppointmentTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleAppointmentManagerImpl implements ScheduleAppointmentManager {

    private final AppointmentService appointmentService;
    private final AppointmentSlotService appointmentSlotService;

    @Override
    public AppointmentDTO schedule(ScheduleAppointmentTemplate appointmentTemplate) {
        List<AppointmentSlot> freeSlotsInRequestedHospitalForDoctor = getFreeSlotsInRequestedHospitalForDoctor(appointmentTemplate);
        LocalDateTime appointmentDateTime = appointmentTemplate.getAppointmentDateTime();
        boolean canSchedule = checkIfProposedAppointmentDateTimeIsFree(freeSlotsInRequestedHospitalForDoctor, appointmentDateTime);
        if (!canSchedule) {
            throw new IllegalArgumentException("Requested date/time is unavailable.");
        }
        AppointmentDTO appointmentDTO = createAppointmentDTO(appointmentTemplate);
        appointmentDTO.setDate(appointmentDateTime);
        return appointmentService.save(appointmentDTO);
    }

    private List<AppointmentSlot> getFreeSlotsInRequestedHospitalForDoctor(ScheduleAppointmentTemplate appointmentTemplate) {
        List<AppointmentSlot> allFreeAppointmentSlotsForDoctor = appointmentSlotService.getAllFreeAppointmentSlotsForDoctor(appointmentTemplate.getDoctorDTO().getId());
        return allFreeAppointmentSlotsForDoctor.stream()
                .filter(appointmentSlot -> appointmentSlot.getHospital().equals(appointmentTemplate.getHospitalAffiliationDTO().getHospitalName()))
                .collect(Collectors.toList());
    }

    private boolean checkIfProposedAppointmentDateTimeIsFree(List<AppointmentSlot> freeAppointmentSlots, LocalDateTime proposedAppointmentDateTime) {
        return freeAppointmentSlots.stream()
                .map(AppointmentSlot::getDateTime)
                .anyMatch(localDateTime -> localDateTime.equals(proposedAppointmentDateTime));
    }

    private AppointmentDTO createAppointmentDTO(ScheduleAppointmentTemplate appointmentTemplate) {
        return AppointmentDTO.builder()
                .patient(appointmentTemplate.getPatientDTO())
                .doctor(appointmentTemplate.getDoctorDTO())
                .hospital(appointmentTemplate.getHospitalAffiliationDTO())
                .build();
    }
}
