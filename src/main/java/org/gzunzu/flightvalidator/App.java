package org.gzunzu.flightvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@Profile("local")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
