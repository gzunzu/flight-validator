package org.gzunzu.flightvalidator.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.configuration.TakeOffRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TakeOffRuleServiceImplTest {

    private static LocalTime NO_TAKEOFF_HOUR;
    private static LocalTime LIMITED_DISTANCE_TAKEOFF_HOUR;
    private static double LIMITED_KM;

    private static AutoCloseable autoCloseable;

    @InjectMocks
    private TakeOffRuleServiceImpl takeOffRuleService;
    @Mock
    private TakeOffRuleConfiguration ruleValues;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(TakeOffRuleServiceImplTest.class);
        NO_TAKEOFF_HOUR = LocalTime.of(20, 0);
        LIMITED_DISTANCE_TAKEOFF_HOUR = LocalTime.of(14, 0);
        LIMITED_KM = 9000;
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception e) {
            log.warn("Error while trying to close {} test class mocks.", TakeOffRuleServiceImplTest.class.getSimpleName());
        }
    }

    @ParameterizedTest
    @ValueSource(shorts = {14, 20, 23})
    void test_mustComply_true(final short hour) {
        final Flight flight = Flight.builder()
                .takeOffTime(LocalTime.of(hour, 0))
                .build();

        this.mockRuleValues();

        final boolean result = this.takeOffRuleService.mustComply(flight);

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(shorts = {0, 8, 13})
    void test_mustComply_false(final short hour) {
        final Flight flight = Flight.builder()
                .takeOffTime(LocalTime.of(hour, 0))
                .build();

        this.mockRuleValues();

        final boolean result = this.takeOffRuleService.mustComply(flight);

        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"20000,10:00", "20000,00:00", "9000,14:00", "8999,13:59", "9000,19:59"})
    void test_isCompliant_true(final double distance, final LocalTime takeOffTime) {
        final ValidationDTO validationDTO = this.initializeValidatedFlightDTO(distance, takeOffTime);

        this.mockRuleValues();

        final boolean result = this.takeOffRuleService.isCompliant(validationDTO);

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,20:00", "9001,14:00"})
    void test_isCompliant_false(final double distance, final LocalTime takeOffTime) {
        final ValidationDTO validationDTO = this.initializeValidatedFlightDTO(distance, takeOffTime);

        this.mockRuleValues();

        final boolean result = this.takeOffRuleService.isCompliant(validationDTO);

        assertThat(result).isFalse();
    }

    private ValidationDTO initializeValidatedFlightDTO(final double distance, final LocalTime takeOffTime) {
        final Flight flight = Flight.builder()
                .takeOffTime(takeOffTime)
                .build();
        return ValidationDTO.builder()
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
        when(this.ruleValues.getLimitedDistanceTakeOffHour())
                .thenReturn(LIMITED_DISTANCE_TAKEOFF_HOUR);
        lenient()
                .when(this.ruleValues.getLimitedKm())
                .thenReturn(LIMITED_KM);
    }
}