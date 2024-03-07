package com.antont.parserserver.config;

import com.antont.parserserver.TimePeriodType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, TimePeriodType> {
    @Override
    public TimePeriodType convert(String source) {
        return TimePeriodType.valueOf(source.toUpperCase());
    }
}