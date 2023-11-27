package org.gzunzu.flightvalidator.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class FlightUtils {

    private static final int R;
    private static final DateTimeFormatter DATE_TIME_FORMATTER;

    static {
        R = 6371;
        DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    }

    private static Double toRad(final Double value) {
        return value * Math.PI / 180;
    }

    public static double getHaversineDistance(final double latitude1, final double longitude1, final double latitude2, final double longitude2) {
        double latDistance = toRad(latitude2 - latitude1);
        double lonDistance = toRad(longitude2 - longitude1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(latitude1)) * Math.cos(toRad(latitude2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Evaluates if the shortest path to go from a departure longitude point to another one is traveling west. International Reference Meridian and
     * negative longitud values are considered.
     *
     * @param departureLongitude the longitude from which we start,
     * @param arrivalLongitude   the longitud to go to.
     * @return true if the shortest path is heading west; false if not, or if it's the same distance because points are oposite sides.
     */
    public static boolean isHeadingWest(final double departureLongitude, final double arrivalLongitude) {
        boolean isHeadingWest = false;
        if (departureLongitude >= 0 && arrivalLongitude >= 0) {
            isHeadingWest = departureLongitude - arrivalLongitude >= 0;
        } else if (departureLongitude < 0 && arrivalLongitude <= 0) {
            isHeadingWest = arrivalLongitude - departureLongitude <= 0;
        } else if (departureLongitude >= 0 && arrivalLongitude < 0) {
            isHeadingWest = departureLongitude - arrivalLongitude < 180;
        } else if (departureLongitude < 0 && arrivalLongitude >= 0) {
            isHeadingWest = arrivalLongitude - departureLongitude > 180;
        }
        return isHeadingWest;
    }

    public static String format(final LocalTime localTime) {
        return DATE_TIME_FORMATTER.format(localTime);
    }

    public static LocalTime format(final String localTime) {
        return LocalTime.parse(localTime);
    }
}