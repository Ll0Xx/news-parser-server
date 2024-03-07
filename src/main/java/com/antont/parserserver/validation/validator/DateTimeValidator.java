package com.antont.parserserver.validation.validator;

import com.antont.parserserver.validation.DateTimeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeValidator implements ConstraintValidator<DateTimeConstraint, String> {

    private String dateTimePattern;

    @Override
    public void initialize(DateTimeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.dateTimePattern = constraintAnnotation.dateTimePattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern, Locale.US);
            LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
