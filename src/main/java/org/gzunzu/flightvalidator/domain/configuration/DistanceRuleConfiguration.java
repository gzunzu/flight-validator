package org.gzunzu.flightvalidator.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.distance")
@Getter
@AllArgsConstructor
public class DistanceRuleConfiguration {

    @Value("${maxKm:12000}")
    private int maxKm;

    @Value("${paxThreshold:250}")
    private short paxThreshold;

    @Value("${limitedByPaxKm:8000}")
    private int limitedByPaxKm;
}
