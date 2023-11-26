package org.gzunzu.flightvalidator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.configuration.DistanceRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.RuleValidatorService;
import org.gzunzu.flightvalidator.domain.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("distanceRuleService")
@Slf4j
@RequiredArgsConstructor
public class DistanceRuleServiceImpl implements RuleValidatorService {

    private final DistanceRuleConfiguration ruleValues;

    @Override
    public boolean mustComply(final Flight flight) {
        return true;
    }

    @Override
    public boolean isCompliant(final ValidatedFlightDTO flightDTO) {
        return this.validateBasicDistance(flightDTO) && this.validatePaxLimitedDistance(flightDTO);
    }

    private boolean validateBasicDistance(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getDistance() <= this.ruleValues.getMaxKm();
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.MAXIMUM_RANGE,
                    this.ruleValues.getMaxKm(),
                    flightDTO.getDistance());
        }
        return isCompliant;
    }

    private boolean validatePaxLimitedDistance(final ValidatedFlightDTO flightDTO) {
        final boolean isCompliant = flightDTO.getFlight().getPaxCount() <= this.ruleValues.getPaxThreshold()
                || flightDTO.getDistance() <= this.ruleValues.getLimitedByPaxKm();
        if (!isCompliant) {
            ValidationUtils.addValidationMessage(flightDTO,
                    RuleValidationMessage.PAX_LIMITED_MAXIMUM_RANGE,
                    this.ruleValues.getPaxThreshold(),
                    this.ruleValues.getLimitedByPaxKm(),
                    flightDTO.getFlight().getPaxCount(),
                    flightDTO.getDistance());
        }
        return isCompliant;
    }
}
