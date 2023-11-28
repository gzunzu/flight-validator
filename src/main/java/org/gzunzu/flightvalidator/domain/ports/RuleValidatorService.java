package org.gzunzu.flightvalidator.domain.ports;

import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;

/**
 * Rule validator service.
 */
public interface RuleValidatorService {

    /**
     * Evaluates if provided flight must comply with a service level defined rule.
     *
     * @param flight the flight to validate.
     * @return true if the flight fits in the rule defined thresholds; false otherwise.
     */
    boolean mustComply(final Flight flight);

    /**
     * Evaluates if provided flight complies with a service level defined rule.
     *
     * @param flightDTO the flight validation DTO containing the flight to validate.
     * @return true if the flight is feasible; false otherwise.
     */
    boolean isCompliant(final ValidationDTO flightDTO);
}
