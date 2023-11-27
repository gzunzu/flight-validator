package org.gzunzu.flightvalidator.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.configuration.WestDestinationRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Coordinates;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.utils.FlightUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.lenient;

@Slf4j
@ExtendWith(MockitoExtension.class)
class WestDestinationRuleServiceImplTest {

    private static LocalTime NO_TAKEOFF_HOUR;
    private static double LIMITED_KM;

    private static AutoCloseable autoCloseable;

    @InjectMocks
    private WestDestinationRuleServiceImpl westDestinationRuleService;
    @Mock
    private WestDestinationRuleConfiguration ruleValues;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(WestDestinationRuleServiceImplTest.class);
        NO_TAKEOFF_HOUR = LocalTime.of(15, 0);
        LIMITED_KM = 3000;
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception e) {
            log.warn("Error while trying to close {} test class mocks.", WestDestinationRuleServiceImplTest.class.getSimpleName());
        }
    }

    @Test
    void test_mustComply_true() {
        final Coordinates coordinates = Coordinates.builder()
                .departureLatitude(0d)
                .departureLongitude(0d)
                .arrivalLatitude(0d)
                .arrivalLongitude(0d)
                .build();
        final Flight flight = Flight.builder()
                .coordinates(coordinates)
                .build();

        try (MockedStatic<FlightUtils> flightUtilsMockedStatic = Mockito.mockStatic(FlightUtils.class)) {
            flightUtilsMockedStatic.when(() -> FlightUtils.isHeadingWest(anyDouble(), anyDouble()))
                    .thenReturn(true);

            final boolean result = this.westDestinationRuleService.mustComply(flight);
            assertThat(result).isTrue();
        }
    }

    @Test
    void test_mustComply_false() {
        final Coordinates coordinates = Coordinates.builder()
                .departureLatitude(0d)
                .departureLongitude(0d)
                .arrivalLatitude(0d)
                .arrivalLongitude(0d)
                .build();
        final Flight flight = Flight.builder()
                .coordinates(coordinates)
                .build();

        try (MockedStatic<FlightUtils> flightUtilsMockedStatic = Mockito.mockStatic(FlightUtils.class)) {
            flightUtilsMockedStatic.when(() -> FlightUtils.isHeadingWest(anyDouble(), anyDouble()))
                    .thenReturn(false);

            final boolean result = this.westDestinationRuleService.mustComply(flight);
            assertThat(result).isFalse();
        }
    }

    @ParameterizedTest
    @CsvSource({"3000,14:59", "3000,00:00"})
    void test_isCompliant_true(final double distance, final LocalTime takeOffTime) {
        final ValidatedFlightDTO validatedFlightDTO = this.initializeValidatedFlightDTO(distance, takeOffTime);

        this.mockRuleValues();

        final boolean result = this.westDestinationRuleService.isCompliant(validatedFlightDTO);

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"3001,14:59", "2999,15:00"})
    void test_isCompliant_false(final double distance, final LocalTime takeOffTime) {
        final ValidatedFlightDTO validatedFlightDTO = this.initializeValidatedFlightDTO(distance, takeOffTime);

        this.mockRuleValues();

        final boolean result = this.westDestinationRuleService.isCompliant(validatedFlightDTO);

        assertThat(result).isFalse();
    }

    private ValidatedFlightDTO initializeValidatedFlightDTO(final double distance, final LocalTime takeOffTime) {
        final Flight flight = Flight.builder()
                .takeOffTime(takeOffTime)
                .build();
        return ValidatedFlightDTO.builder()
                .flight(flight)
                .distance(distance)
                .feasible(false)
                .validationMessages(new EnumMap<>(RuleValidationMessage.class))
                .build();
    }

    private void mockRuleValues() {
        lenient()
                .when(this.ruleValues.getNoTakeOffHour())
                .thenReturn(NO_TAKEOFF_HOUR);
        lenient()
                .when(this.ruleValues.getLimitedKm())
                .thenReturn(LIMITED_KM);
    }
}