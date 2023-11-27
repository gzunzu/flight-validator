package org.gzunzu.flightvalidator.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Pattern(regexp = "^[A-Z]{3}\\d{3}[A-Z]{1}$", message = "Flight number must be formed by 3 uppercase letters, " +
            "followed by 3 digits and another additional uppercase letter.")
    private String flightNumber;

    @JsonProperty("paxCount")
    @NotNull(message = "Pax count (paxCount) should not be null")
    @PositiveOrZero(message = "Pax count should be a positive integer, or zero.")
    private Short paxCount;

    @JsonProperty("takeOffTime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime takeOffTime;

    @JsonProperty("coordinates")
    @NotNull(message = "Coordinates data (departure and arrival Lat and Long values) should not be null.")
    @Valid
    private Coordinates coordinates;
}
