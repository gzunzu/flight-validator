package org.gzunzu.flightvalidator.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "validation-rules.takeOff")
@Getter
@AllArgsConstructor
public class TakeOffRuleConfiguration {

    @Value("${noTakeOffHour:20}")
    private short noTakeOffHour;

    @Value("${limitedDistanceTakeOffHour:14}")
    private short limitedDistanceTakeOffHour;

    @Value("${limitedKm:9000}")
    private int limitedKm;
}
