package org.gzunzu.flightvalidator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.configuration.WestDestinationRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.Travel;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.RuleValidatorService;
import org.gzunzu.flightvalidator.domain.utils.FlightUtils;
import org.gzunzu.flightvalidator.domain.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("westDestinationRuleService")
@Slf4j
@RequiredArgsConstructor
public class WestDestinationRuleServiceImpl implements RuleValidatorService {

    private final WestDestinationRuleConfiguration ruleValues;

    @Override
    public boolean mustComply(final Flight flight) {
        return this.isFlightHeadingWest(flight.getTravel());
    }

    private boolean isFlightHeadingWest(final Travel travel) {
        return FlightUtils.getShortestDistance(travel.getDepartureLongitude(), travel.getArrivalLongitude()) < 0;
    }

    @Override
    public boolean isCompliant(final ValidatedFlightDTO flightDTO) {
        return this.validateNoTakeOffHour(flightDTO) && this.validateLimitedDistance(flightDTO);
    }

    private boolean validateNoTakeOffHour(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getFlight().getTakeOffTime().getHour() < this.ruleValues.getNoTakeOffHour();
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.WEST_TAKEOFF_LIMIT,
                    this.ruleValues.getNoTakeOffHour(),
                    flightDTO.getFlight().getTakeOffTime().getHour());
        }
        return isCompliant;
    }

    private boolean validateLimitedDistance(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getDistance() <= this.ruleValues.getLimitedKm();
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.WEST_MAXIMUM_RANGE,
                    this.ruleValues.getLimitedKm(),
                    flightDTO.getDistance());
        }
        return isCompliant;
    }
}
