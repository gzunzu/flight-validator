package org.gzunzu.flightvalidator.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Setter
    @JsonProperty("feasible")
    private Boolean feasible;
    @JsonProperty("validationMessages")
    private EnumMap<RuleValidationMessage, String> validationMessages;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("flight")
    private Flight flight;

    public ValidatedFlightDTO(final Flight flight, final Double distance) {
        this.flight = flight;
        this.distance = distance;
        this.validationMessages = new EnumMap<>(RuleValidationMessage.class);
    }
}
