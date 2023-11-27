package org.gzunzu.flightvalidator.domain.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.west-destination")
@Getter
public class WestDestinationRuleConfiguration {

    @Value("${noTakeOffHour:15:00}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime noTakeOffHour;

    @Value("${limitedKm:3000}")
    private double limitedKm;
}
