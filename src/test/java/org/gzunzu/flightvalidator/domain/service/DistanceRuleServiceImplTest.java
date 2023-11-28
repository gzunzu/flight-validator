package org.gzunzu.flightvalidator.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.configuration.DistanceRuleConfiguration;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DistanceRuleServiceImplTest {

    private static double MAX_KM;
    private static short PAX_THRESHOLD;
    private static double LIMITED_BY_PAX_KM;

    private static AutoCloseable autoCloseable;

    @InjectMocks
    private DistanceRuleServiceImpl distanceRuleService;
    @Mock
    private DistanceRuleConfiguration ruleValues;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(DistanceRuleServiceImplTest.class);
        MAX_KM = 12000D;
        PAX_THRESHOLD = 250;
        LIMITED_BY_PAX_KM = 8000;
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception e) {
            log.warn("Error while trying to close {} test class mocks.", DistanceRuleServiceImplTest.class.getSimpleName());
        }
    }

    @Test
    void test_mustComply() {
        final Flight flight = mock(Flight.class);

        final boolean result = this.distanceRuleService.mustComply(flight);

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"11000,100", "7999,251", "8001,250"})
    void test_isCompliant_true(final double distance, final short paxCount) {
        final ValidationDTO validationDTO = this.initializeValidatedFlightDTO(distance, paxCount);

        this.mockRuleValues();

        final boolean result = this.distanceRuleService.isCompliant(validationDTO);

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"11000,251", "13000,250", "8001,251"})
    void test_isCompliant_false(final double distance, final short paxCount) {
        final ValidationDTO validationDTO = this.initializeValidatedFlightDTO(distance, paxCount);

        this.mockRuleValues();

        final boolean result = this.distanceRuleService.isCompliant(validationDTO);

        assertThat(result).isFalse();
    }

    private ValidationDTO initializeValidatedFlightDTO(final double distance, final short paxCount) {
        final Flight flight = Flight.builder()
                .paxCount(paxCount)
                .build();
        return ValidationDTO.builder()
                .flight(flight)
                .distance(distance)
                .feasible(false)
                .validationMessages(new EnumMap<>(RuleValidationMessage.class))
                .build();
    }

    private void mockRuleValues() {
        when(this.ruleValues.getMaxKm())
                .thenReturn(MAX_KM);
        when(this.ruleValues.getPaxThreshold())
                .thenReturn(PAX_THRESHOLD);
        lenient()
                .when(this.ruleValues.getLimitedByPaxKm())
                .thenReturn(LIMITED_BY_PAX_KM);
    }
}