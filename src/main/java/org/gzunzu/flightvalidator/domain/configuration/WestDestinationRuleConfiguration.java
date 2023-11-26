package org.gzunzu.flightvalidator.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.west-destination")
@Getter
@AllArgsConstructor
public class WestDestinationRuleConfiguration {

    @Value("${noTakeOffHour:15}")
    private short noTakeOffHour;

    @Value("${limitedKm:3000}")
    private int limitedKm;
}
