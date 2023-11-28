package org.gzunzu.flightvalidator.domain.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integration.geoapify")
@Getter
public class GeoApifyConfiguration {

    @Value("${key}")
    private String key;
}
