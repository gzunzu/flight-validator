package org.gzunzu.flightvalidator.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.EnumMap;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationDTO implements Serializable {

    private static final long serialVersionUID = 123456789L;

    @Setter
    @JsonProperty("feasible")
    private Boolean feasible;
    @JsonProperty("distance")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double distance;
    @JsonProperty("direction")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String heading;
    @JsonProperty("validationMessages")
    private EnumMap<RuleValidationMessage, String> validationMessages;
    @JsonIgnore
    private Flight flight;
    @JsonIgnore
    private Direction direction;

    public ValidationDTO(final Flight flight, final Double distance, final Direction direction) {
        this.flight = flight;
        this.distance = distance;
        this.direction = direction;
        this.heading = direction.toString();
        this.validationMessages = new EnumMap<>(RuleValidationMessage.class);
    }

    public ValidationDTO(final Flight flight) {
        this.flight = flight;
        this.direction = new Direction();
        this.validationMessages = new EnumMap<>(RuleValidationMessage.class);
    }
}
