package org.gzunzu.flightvalidator.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Valid
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 111111111L;

    @JsonProperty("departureLatitude")
    @NotNull(message = "Departure latitude should not be null")
    @Max(value = 90, message = "Departure latitude value cannot be greater than 90.")
    @Min(value = -90, message = "Departure latitude value cannot be less than -90.")
    private Double departureLatitude;

    @JsonProperty("departureLongitude")
    @NotNull(message = "Departure longitude should not be null")
    @Max(value = 180, message = "Departure longitude value cannot be greater than 180.")
    @Min(value = -180, message = "Departure longitude value cannot be less than -180.")
    private Double departureLongitude;

    @JsonProperty("arrivalLatitude")
    @NotNull(message = "Arrival latitude should not be null")
    @Max(value = 90, message = "Arrival latitude value cannot be greater than 90.")
    @Min(value = -90, message = "Arrival latitude value cannot be less than -90.")
    private Double arrivalLatitude;

    @JsonProperty("arrivalLongitude")
    @NotNull(message = "Arrival longitude should not be null")
    @Max(value = 180, message = "Arrival longitude value cannot be greater than 180.")
    @Min(value = -180, message = "Arrival longitude value cannot be less than -180.")
    private Double arrivalLongitude;
}
