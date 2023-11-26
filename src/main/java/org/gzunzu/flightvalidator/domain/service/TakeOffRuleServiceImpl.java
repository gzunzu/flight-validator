package org.gzunzu.flightvalidator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.configuration.TakeOffRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.RuleValidatorService;
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
        return flight.getTakeOffTime().getHour() >= this.ruleValues.getLimitedDistanceTakeOffHour();
    }

    @Override
    public boolean isCompliant(final ValidatedFlightDTO flightDTO) {
        return this.validateNoTakeOffHour(flightDTO) && this.validateLimitedDistanceByTakeOffHour(flightDTO);
    }

    private boolean validateNoTakeOffHour(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getFlight().getTakeOffTime().getHour() < this.ruleValues.getNoTakeOffHour();
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.TAKEOFF_LIMIT,
                    this.ruleValues.getNoTakeOffHour(),
                    flightDTO.getFlight().getTakeOffTime().getHour());
        }
        return isCompliant;
    }

    private boolean validateLimitedDistanceByTakeOffHour(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getDistance() <= this.ruleValues.getLimitedKm();
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.TAKEOFF_MAXIMUM_RANGE,
                    this.ruleValues.getLimitedDistanceTakeOffHour(),
                    this.ruleValues.getLimitedKm(),
                    flightDTO.getFlight().getTakeOffTime().getHour(),
                    flightDTO.getDistance());
        }
        return isCompliant;
    }
}
