package org.gzunzu.flightvalidator.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.gzunzu.flightvalidator.domain.model.Coordinates;
import org.gzunzu.flightvalidator.domain.model.Direction;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;
import org.gzunzu.flightvalidator.domain.ports.FlightValidatorService;
import org.gzunzu.flightvalidator.domain.ports.RuleValidatorService;
import org.gzunzu.flightvalidator.domain.utils.FlightUtils;
import org.gzunzu.flightvalidator.domain.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlightValidatorServiceImpl implements FlightValidatorService {

    private final RuleValidatorService distanceRuleService;
    private final RuleValidatorService takeOffRuleService;
    private final RuleValidatorService westDestinationRuleService;

    public FlightValidatorServiceImpl(@Qualifier("distanceRuleService") final RuleValidatorService distanceRuleService,
                                      @Qualifier("takeOffRuleService") final RuleValidatorService takeOffRuleService,
                                      @Qualifier("westDestinationRuleService") final RuleValidatorService westDestinationRuleService) {
        this.distanceRuleService = distanceRuleService;
        this.takeOffRuleService = takeOffRuleService;
        this.westDestinationRuleService = westDestinationRuleService;
    }

    @Override
    public ValidationDTO validateFlight(final Flight flight) {
        final ValidationDTO flightDTO = new ValidationDTO(flight, this.calculateDistance(flight.getCoordinates()), this.getDirection(flight.getCoordinates()));
        this.evaluateFeasible(flightDTO);
        return flightDTO;
    }

    private double calculateDistance(final Coordinates coordinates) {
        return FlightUtils.getHaversineDistance(coordinates.getDepartureLatitude(),
                coordinates.getArrivalLongitude(),
                coordinates.getDepartureLatitude(),
                coordinates.getDepartureLongitude());
    }

    private Direction getDirection(final Coordinates coordinates) {
        return FlightUtils.getDirection(coordinates);
    }

    @SuppressWarnings("java:S3878")
    private void evaluateFeasible(final ValidationDTO flightDTO) {
        final boolean feasible = BooleanUtils.and(new boolean[]{this.validateDistanceRule(flightDTO),
                this.validateTakeOffRule(flightDTO),
                this.validateWestDestinationRule(flightDTO)});
        if (feasible) {
            ValidationUtils.addValidationMessage(flightDTO, RuleValidationMessage.VALID);
        }
        flightDTO.setFeasible(feasible);
    }

    private boolean validateDistanceRule(final ValidationDTO flightDTO) {
        return !this.distanceRuleService.mustComply(flightDTO.getFlight()) || this.distanceRuleService.isCompliant(flightDTO);
    }

    private boolean validateTakeOffRule(final ValidationDTO flightDTO) {
        return !this.takeOffRuleService.mustComply(flightDTO.getFlight()) || this.takeOffRuleService.isCompliant(flightDTO);
    }

    private boolean validateWestDestinationRule(final ValidationDTO flightDTO) {
        return !this.westDestinationRuleService.mustComply(flightDTO.getFlight()) || this.westDestinationRuleService.isCompliant(flightDTO);
    }
}
