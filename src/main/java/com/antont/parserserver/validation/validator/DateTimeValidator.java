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

    /**
     * Validates a date-time string against a specified pattern.
     * This method checks if the provided string is not null and not empty, and then attempts to parse it into a LocalDateTime object using the specified pattern.
     * If the parsing is successful, the method returns true, indicating that the string is valid.
     * If the parsing fails (due to an exception) or if the string is null or empty, the method returns false, indicating that the string is not valid.
     *
     * @param value The date-time string to be validated.
     * @param constraintValidatorContext The context for the constraint validator, which can be used to add custom error messages or other validation logic.
     * @return true if the date-time string is valid according to the specified pattern, false otherwise.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // Check if the value is not null and not empty.
        if (value != null && !value.isEmpty()) {
            try {
                // Attempt to parse the value into a LocalDateTime object using the specified pattern.
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern, Locale.US);
                LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
                // If parsing is successful, return true.
                return true;
            } catch (Exception e) {
                // If parsing fails, return false.
                return false;
            }
        } else {
            // If the value is null or empty, return true.
            // This behavior can be adjusted based on the desired validation logic.
            return true;
        }
    }
}
