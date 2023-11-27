package org.gzunzu.flightvalidator.domain.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.distance")
@Getter
public class DistanceRuleConfiguration {

    @Value("${maxKm:12000}")
    private double maxKm;

    @Value("${paxThreshold:250}")
    private short paxThreshold;

    @Value("${limitedByPaxKm:8000}")
    private double limitedByPaxKm;
}
