package org.gzunzu.flightvalidator.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Valid
public class Flight implements Serializable {

    private static final long serialVersionUID = 987654321L;

    @JsonProperty("flightNumber")
    @NotBlank
    private String flightNumber;

    @JsonProperty("paxCount")
    @PositiveOrZero
    private short paxCount;

    @JsonProperty("takeOffTime")
    @NotNull
    private LocalTime takeOffTime;

    @JsonProperty("departureLatitude")
    @NotNull
    @Max(value = 90, message = "Departure latitude value cannot be greater than 90.")
    @Min(value = -90, message = "Departure latitude value cannot be less than -90.")
    private double departureLatitude;

    @JsonProperty("departureLongitude")
    @NotNull
    @Max(value = 180, message = "Departure longitude value cannot be greater than 180.")
    @Min(value = -180, message = "Departure longitude value cannot be less than -180.")
    private double departureLongitude;

    @JsonProperty("arrivalLatitude")
    @NotNull
    @Max(value = 90, message = "Departure latitude value cannot be greater than 90.")
    @Min(value = -90, message = "Departure latitude value cannot be less than -90.")
    private double arrivalLatitude;

    @JsonProperty("arrivalLongitude")
    @NotNull
    @Max(value = 180, message = "Arrival longitude value cannot be greater than 180.")
    @Min(value = -180, message = "Arrival longitude value cannot be less than -180.")
    private double arrivalLongitude;
}
