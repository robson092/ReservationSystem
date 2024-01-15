package com.example.reservation.enums;

public enum SpecializationEnum {

    ANESTHESIOLOGIST("ANESTHESIOLOGIST"),
    CARDIOLOGIST("CARDIOLOGIST"),
    DERMATOLOGIST("DERMATOLOGIST"),
    ENDOCRINOLOGIST("ENDOCRINOLOGIST"),
    NEUROLOGIST("NEUROLOGIST"),
    PEDIATRIST("PEDIATRIST"),
    PSYCHIATRIST("PSYCHIATRIST"),
    SURGEON("SURGEON"),
    INTERNIST("INTERNIST");

    private final String specialization;

    SpecializationEnum(String specialization) {
        this.specialization = specialization;
    }


    public String getSpecialization() {
        return specialization;
    }
}
