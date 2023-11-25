package org.gzunzu.flightvalidator.domain.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.general")
public class GeneralDistanceRuleConfiguration {

    @Value("${maxKm:12000}")
    private int maxKm;

    @Value("${paxThreshold:250}")
    private short paxThreshold;

    @Value("${limitedByPaxKm:8000}")
    private int limitedByPaxKm;
}
