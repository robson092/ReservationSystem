package com.example.reservation.model;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class AppointmentSlot {

    private int doctorId;
    private String hospital;
    private LocalDateTime dateTime;
    private DayOfWeek dayOfWeek;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentSlot)) return false;
        AppointmentSlot that = (AppointmentSlot) o;
        return doctorId == that.doctorId && Objects.equals(hospital, that.hospital) && Objects.equals(dateTime, that.dateTime) && dayOfWeek == that.dayOfWeek;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, hospital, dateTime, dayOfWeek);
    }
}
