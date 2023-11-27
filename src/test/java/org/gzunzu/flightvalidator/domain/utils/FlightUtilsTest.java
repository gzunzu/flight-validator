package org.gzunzu.flightvalidator.domain.utils;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FlightUtilsTest {

    @ParameterizedTest
    @CsvSource({"48.8566,2.3522,33.941,-118.4085,9104",
            "51.5073,-0.1277,33.941,-118.4085,8774",
            "-37.6732,144.8431,51.5073,-0.1277,16885",
            "53.4264,-6.2499,41.2974,2.0832,1485"})
    void test_isHeadingWest_true(final double departureLatitude, final double departureLongitude, final double arrivalLatitude, final double arrivalLongitude, final double expected) {
        final double result = FlightUtils.getHaversineDistance(departureLatitude, departureLongitude, arrivalLatitude, arrivalLongitude);
        assertThat((int) result).isEqualTo((int) expected);
    }

    @ParameterizedTest
    @CsvSource({"175,10", "175,-1", "175,0", "-175,10", "-175,-179", "0,-175"})
    void test_isHeadingWest_true(final double departureLongitude, final double arrivalLongitude) {
        final boolean result = FlightUtils.isHeadingWest(departureLongitude, arrivalLongitude);
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"175,-10", "175,179", "-175,0", "-175,-10", "-175,1", "0,175"})
    void test_isHeadingWest_false(final double departureLongitude, final double arrivalLongitude) {
        final boolean result = FlightUtils.isHeadingWest(departureLongitude, arrivalLongitude);
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"22,05,22:05", "9,5,09:05"})
    void test_format(final short hour, final short minute, final String expected) {
        final String result = FlightUtils.format(LocalTime.of(hour, minute));
        assertThat(result).isEqualTo(expected);
    }
}