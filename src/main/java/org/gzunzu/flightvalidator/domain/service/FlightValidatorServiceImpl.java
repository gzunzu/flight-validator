package org.gzunzu.flightvalidator.domain.service;

import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.ports.FlightValidatorService;
import org.springframework.stereotype.Service;

@Service
public class FlightValidatorServiceImpl implements FlightValidatorService {

    @Override
    public boolean validateFlight(final Flight flight) {
        return false;
    }
}
