package org.gzunzu.flightvalidator.domain.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.take-off")
@Getter
public class TakeOffRuleConfiguration {

    @Value("${noTakeOffHour:20:00}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime noTakeOffHour;

    @Value("${limitedDistanceTakeOffHour:14:00}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime limitedDistanceTakeOffHour;

    @Value("${limitedKm:9000}")
    private double limitedKm;
}
