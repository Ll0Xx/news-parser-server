package com.antont.parserserver.dto;

import com.antont.parserserver.validation.DateTimeConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CreateNewsDto(
        @NotNull @Size(max = 255) String headline,
        @Size(max = 255) String description,
        @NotNull @DateTimeConstraint String time,
        @NotNull @Size(max = 255) @URL String link) {
}
