package org.gzunzu.flightvalidator.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.model.Coordinates;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.RuleValidatorService;
import org.gzunzu.flightvalidator.domain.utils.FlightUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class FlightValidatorServiceImplTest {

    @InjectMocks
    private FlightValidatorServiceImpl flightValidatorService;
    @Mock
    private RuleValidatorService distanceRuleService;
    @Mock
    private RuleValidatorService takeOffRuleService;
    @Mock
    private RuleValidatorService westDestinationRuleService;

    @BeforeEach
    void init() {
        this.distanceRuleService = mock(DistanceRuleServiceImpl.class);
        this.takeOffRuleService = mock(TakeOffRuleServiceImpl.class);
        this.westDestinationRuleService = mock(WestDestinationRuleServiceImpl.class);
        this.flightValidatorService = new FlightValidatorServiceImpl(this.distanceRuleService,
                this.takeOffRuleService,
                this.westDestinationRuleService);
    }

    @Test
    void test_validateFlight_valid_mustNotComplyAny() {
        final Coordinates coordinates = new Coordinates(0d, 0d, 0d, 0d);
        final Flight flight = Flight.builder()
                .coordinates(coordinates)
                .build();

        when(this.distanceRuleService.mustComply(eq(flight)))
                .thenReturn(false);
        when(this.takeOffRuleService.mustComply(eq(flight)))
                .thenReturn(false);
        when(this.westDestinationRuleService.mustComply(eq(flight)))
                .thenReturn(false);

        try (MockedStatic<FlightUtils> flightUtilsMockedStatic = Mockito.mockStatic(FlightUtils.class)) {
            flightUtilsMockedStatic.when(() -> FlightUtils.getHaversineDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                    .thenReturn(0d);

            final ValidatedFlightDTO result = this.flightValidatorService.validateFlight(flight);

            assertThat(result.getFeasible()).isTrue();
        }
    }

    @Test
    void test_validateFlight_valid_mustComplyAll_noneValid() {
        final Coordinates coordinates = new Coordinates(0d, 0d, 0d, 0d);
        final Flight flight = Flight.builder()
                .coordinates(coordinates)
                .build();

        when(this.distanceRuleService.mustComply(eq(flight)))
                .thenReturn(true);
        when(this.takeOffRuleService.mustComply(eq(flight)))
                .thenReturn(true);
        when(this.westDestinationRuleService.mustComply(eq(flight)))
                .thenReturn(true);
        when(this.distanceRuleService.isCompliant(any(ValidatedFlightDTO.class)))
                .thenReturn(false);
        when(this.takeOffRuleService.isCompliant(any(ValidatedFlightDTO.class)))
                .thenReturn(false);
        when(this.westDestinationRuleService.isCompliant(any(ValidatedFlightDTO.class)))
                .thenReturn(false);

        try (MockedStatic<FlightUtils> flightUtilsMockedStatic = Mockito.mockStatic(FlightUtils.class)) {
            flightUtilsMockedStatic.when(() -> FlightUtils.getHaversineDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                    .thenReturn(0d);

            final ValidatedFlightDTO result = this.flightValidatorService.validateFlight(flight);

            assertThat(result.getFeasible()).isFalse();
        }
    }

    @Test
    void test_validateFlight_valid_mustComplyAll_allValid() {
        final Coordinates coordinates = new Coordinates(0d, 0d, 0d, 0d);
        final Flight flight = Flight.builder()
                .coordinates(coordinates)
                .build();

        when(this.distanceRuleService.mustComply(eq(flight)))
                .thenReturn(true);
        when(this.takeOffRuleService.mustComply(eq(flight)))
                .thenReturn(true);
        when(this.westDestinationRuleService.mustComply(eq(flight)))
                .thenReturn(true);
        when(this.distanceRuleService.isCompliant(any(ValidatedFlightDTO.class)))
                .thenReturn(true);
        when(this.takeOffRuleService.isCompliant(any(ValidatedFlightDTO.class)))
                .thenReturn(true);
        when(this.westDestinationRuleService.isCompliant(any(ValidatedFlightDTO.class)))
                .thenReturn(true);

        try (MockedStatic<FlightUtils> flightUtilsMockedStatic = Mockito.mockStatic(FlightUtils.class)) {
            flightUtilsMockedStatic.when(() -> FlightUtils.getHaversineDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                    .thenReturn(0d);

            final ValidatedFlightDTO result = this.flightValidatorService.validateFlight(flight);

            assertThat(result.getFeasible()).isTrue();
        }
    }
}