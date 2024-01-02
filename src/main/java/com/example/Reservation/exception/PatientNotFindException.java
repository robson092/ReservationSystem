package com.example.Reservation.exception;

public class PatientNotFindException extends RuntimeException {
    PatientNotFindException(Integer id) {
        super("Could not find patient " + id);
    }
}
