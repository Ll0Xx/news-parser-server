package com.antont.parserserver.dto;

import com.antont.parserserver.validation.DateTimeConstraint;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UpdateNewsDto(
        @Size(max = 255) String headline,
        @Size(max = 255) String description,
        @DateTimeConstraint String time,
        @Size(max = 255) @URL String link) {
}
