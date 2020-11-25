package com.epam.esm.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return attribute.toMillis();
    }

    @Override
    public Duration convertToEntityAttribute(Long duration) {
        return Duration.of(duration, ChronoUnit.MILLIS);
    }
}
