package com.antont.parserserver.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@Configuration
@ConfigurationProperties(prefix = "rest.news")
public class NewsProperties {
    @NotNull
    @Min(0)
    private String dateTimeFormat;

}