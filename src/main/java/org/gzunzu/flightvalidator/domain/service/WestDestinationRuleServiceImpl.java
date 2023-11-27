package org.gzunzu.flightvalidator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.gzunzu.flightvalidator.domain.configuration.WestDestinationRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Coordinates;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
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
        return this.isFlightHeadingWest(flight.getCoordinates());
    }

    private boolean isFlightHeadingWest(final Coordinates coordinates) {
        return FlightUtils.isHeadingWest(coordinates.getDepartureLongitude(), coordinates.getArrivalLongitude());
    }

    @Override
    @SuppressWarnings("java:S3878")
    public boolean isCompliant(final ValidatedFlightDTO flightDTO) {
        return BooleanUtils.and(new boolean[]{this.validateNoTakeOffHour(flightDTO), this.validateLimitedDistance(flightDTO)});
    }

    private boolean validateNoTakeOffHour(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getFlight().getTakeOffTime().isBefore(this.ruleValues.getNoTakeOffHour());
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.WEST_TAKEOFF_LIMIT,
                    FlightUtils.format(this.ruleValues.getNoTakeOffHour()),
                    FlightUtils.format(flightDTO.getFlight().getTakeOffTime()));
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
