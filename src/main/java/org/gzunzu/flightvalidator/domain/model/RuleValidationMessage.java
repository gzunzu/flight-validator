package org.gzunzu.flightvalidator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RuleValidationMessage {
    VALID("Provided flight is feasible."),
    MAXIMUM_RANGE("The maximum flight range of the airplane is %.2f km. Provided flight distance is %.2f km."),
    PAX_LIMITED_MAXIMUM_RANGE("The maximum flight range for flights over %s passengers is %.2f km. " +
            "Provided flight passengers count is %s and distance is %.2f km."),
    TAKEOFF_LIMIT("There shall be no take-offs after %s h. Provided flight takes off at %s h."),
    TAKEOFF_MAXIMUM_RANGE("Flights taking off after %s h can only travel %.2f km. Provided flight takes off at %s h and its distance is %.2f km."),
    WEST_TAKEOFF_LIMIT("Flights heading west must take off before %s h. Provided flight takes off at %s h."),
    WEST_MAXIMUM_RANGE("Flights heading west should not travel further than %.2f km. Provided flight distance is %.2f km."),
    UNPROCESSABLE_ENTITY("Not valid flight configuration values provided: %s.");

    private final String message;
}
