package org.gzunzu.flightvalidator.domain.ports;

import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;

public interface FlightValidatorService {

    ValidatedFlightDTO validateFlight(final Flight flight);
}
