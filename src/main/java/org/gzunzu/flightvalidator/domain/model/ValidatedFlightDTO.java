package org.gzunzu.flightvalidator.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.EnumMap;

@Getter
public class ValidatedFlightDTO implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private final Flight flight;

    private final double distance;
    private final EnumMap<RuleValidationMessage, String> validationMessages;
    @Setter
    private Boolean feasible;

    public ValidatedFlightDTO(final Flight flight, final double distance) {
        this.flight = flight;
        this.distance = distance;
        this.validationMessages = new EnumMap<>(RuleValidationMessage.class);
    }
}
