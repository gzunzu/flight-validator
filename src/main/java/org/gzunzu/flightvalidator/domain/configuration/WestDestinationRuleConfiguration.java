package org.gzunzu.flightvalidator.domain.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.west-destination")
@Getter
public class WestDestinationRuleConfiguration {

    @Value("${noTakeOffHour:15}")
    private short noTakeOffHour;

    @Value("${limitedKm:3000}")
    private double limitedKm;
}
