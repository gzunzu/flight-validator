package org.gzunzu.flightvalidator.domain.ports;

import org.gzunzu.flightvalidator.domain.model.Flight;

public interface FlightValidatorService {

    boolean validateFlight(final Flight flight);
}
