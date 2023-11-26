package org.gzunzu.flightvalidator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RuleValidationMessage {
    VALID("Provided flight is feasible."),
    MAXIMUM_RANGE("The maximum flight range of the airplane is %s km. Provided flight distance is %s km."),
    PAX_LIMITED_MAXIMUM_RANGE("The maximum flight range for flights over %s passengers is %s. " +
            "Provided flight passengers count is %s and distance is %s km."),
    TAKEOFF_LIMIT("There shall be no take-offs after %s:00 h. Provided flight takes off at %s:00 h."),
    TAKEOFF_MAXIMUM_RANGE("Flights taking off after %s:00 h can only travel %s km. Provided flight takes off at %s:00 h and distance is %s km."),
    WEST_TAKEOFF_LIMIT("Flights going West must take off before %s:00 h. Provided flight takes off at %s:00 h."),
    WEST_MAXIMUM_RANGE("Flights heading west should not travel further than %s km. Provided flight distance is %s km."),
    UNEXPECTED_ERROR("Something went wrong while validating flight configuration values.");

    private final String message;
}
