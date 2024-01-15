package com.example.reservation.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SpecializationConverter implements AttributeConverter<SpecializationEnum, String> {

    @Override
    public String convertToDatabaseColumn(SpecializationEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getSpecialization();
    }

    @Override
    public SpecializationEnum convertToEntityAttribute(String specialization) {
        if (specialization == null) {
            return null;
        }
        return Stream.of(SpecializationEnum.values())
                .filter(s -> s.getSpecialization().equals(specialization))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find specialization."));
    }
}
