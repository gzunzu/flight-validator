package org.gzunzu.flightvalidator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.FlightValidatorService;
import org.gzunzu.flightvalidator.domain.ports.RuleValidatorService;
import org.gzunzu.flightvalidator.domain.utils.FlightUtils;
import org.gzunzu.flightvalidator.domain.utils.ValidationUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightValidatorServiceImpl implements FlightValidatorService {

    private final RuleValidatorService distanceRuleService;
    private final RuleValidatorService takeOffRuleService;
    private final RuleValidatorService westDestinationRuleService;

    @Override
    public ValidatedFlightDTO validateFlight(final Flight flight) {
        final ValidatedFlightDTO flightDTO = new ValidatedFlightDTO(flight, this.calculateDistance(flight));
        this.evaluateFeasible(flightDTO);
        return flightDTO;
    }

    private double calculateDistance(final Flight flight) {
        return FlightUtils.getHaversineDistance(flight.getDepartureLatitude(),
                flight.getArrivalLongitude(),
                flight.getDepartureLatitude(),
                flight.getDepartureLongitude());
    }

    @SuppressWarnings("java:S3878")
    private void evaluateFeasible(final ValidatedFlightDTO flightDTO) {
        final boolean feasible = BooleanUtils.and(new boolean[]{this.validateDistanceRule(flightDTO),
                this.validateTakeOffRule(flightDTO),
                this.validateWestDestinationRule(flightDTO)});
        if (feasible) {
            ValidationUtils.addValidationMessage(flightDTO, RuleValidationMessage.VALID);
        }
        flightDTO.setFeasible(feasible);
    }

    private boolean validateDistanceRule(final ValidatedFlightDTO flightDTO) {
        return !this.distanceRuleService.mustComply(flightDTO.getFlight()) || this.distanceRuleService.isCompliant(flightDTO);
    }

    private boolean validateTakeOffRule(final ValidatedFlightDTO flightDTO) {
        return !this.takeOffRuleService.mustComply(flightDTO.getFlight()) || this.takeOffRuleService.isCompliant(flightDTO);
    }

    private boolean validateWestDestinationRule(final ValidatedFlightDTO flightDTO) {
        return !this.westDestinationRuleService.mustComply(flightDTO.getFlight()) || this.westDestinationRuleService.isCompliant(flightDTO);
    }
}
