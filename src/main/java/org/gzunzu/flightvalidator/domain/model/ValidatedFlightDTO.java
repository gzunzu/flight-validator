package org.gzunzu.flightvalidator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.EnumMap;

@Getter
@Builder
@AllArgsConstructor
public class ValidatedFlightDTO implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private Flight flight;

    private double distance;
    private EnumMap<RuleValidationMessage, String> validationMessages;
    @Setter
    private Boolean feasible;

    public ValidatedFlightDTO(final Flight flight, final double distance) {
        this.flight = flight;
        this.distance = distance;
        this.validationMessages = new EnumMap<>(RuleValidationMessage.class);
    }
}
