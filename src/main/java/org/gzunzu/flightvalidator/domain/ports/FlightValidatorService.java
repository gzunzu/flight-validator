package org.gzunzu.flightvalidator.domain.ports;

import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;

public interface FlightValidatorService {

    ValidationDTO validateFlight(final Flight flight);
}
