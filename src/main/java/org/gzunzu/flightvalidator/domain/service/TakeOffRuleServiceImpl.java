package org.gzunzu.flightvalidator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.gzunzu.flightvalidator.domain.configuration.TakeOffRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.RuleValidatorService;
import org.gzunzu.flightvalidator.domain.utils.FlightUtils;
import org.gzunzu.flightvalidator.domain.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("takeOffRuleService")
@Slf4j
@RequiredArgsConstructor
public class TakeOffRuleServiceImpl implements RuleValidatorService {

    private final TakeOffRuleConfiguration ruleValues;

    @Override
    public boolean mustComply(final Flight flight) {
        return this.evaluateTakeOffHour(flight);
    }

    private boolean evaluateTakeOffHour(final Flight flight) {
        return !this.ruleValues.getLimitedDistanceTakeOffHour().isAfter(flight.getTakeOffTime());
    }

    @Override
    @SuppressWarnings("java:S3878")
    public boolean isCompliant(final ValidatedFlightDTO flightDTO) {
        return BooleanUtils.and(new boolean[]{this.validateNoTakeOffHour(flightDTO), this.validateLimitedDistanceByTakeOffHour(flightDTO)});
    }

    private boolean validateNoTakeOffHour(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getFlight().getTakeOffTime().isBefore(this.ruleValues.getNoTakeOffHour());
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.TAKEOFF_LIMIT,
                    FlightUtils.format(this.ruleValues.getNoTakeOffHour()),
                    FlightUtils.format(flightDTO.getFlight().getTakeOffTime()));
        }
        return isCompliant;
    }

    private boolean validateLimitedDistanceByTakeOffHour(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getFlight().getTakeOffTime().isBefore(this.ruleValues.getLimitedDistanceTakeOffHour())
                || flightDTO.getDistance() <= this.ruleValues.getLimitedKm();
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.TAKEOFF_MAXIMUM_RANGE,
                    FlightUtils.format(this.ruleValues.getLimitedDistanceTakeOffHour()),
                    this.ruleValues.getLimitedKm(),
                    FlightUtils.format(flightDTO.getFlight().getTakeOffTime()),
                    flightDTO.getDistance());
        }
        return isCompliant;
    }
}
