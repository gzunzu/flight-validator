package org.gzunzu.flightvalidator.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightUtils {

    private static final int R = 6371;

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
     * Calculate the shortest distance considering the possibility of crossing the International Date Line
     *
     * @param departureLongitude departure longitude.
     * @param arrivalLongitude   arrival longitude.
     * @return the shortest difference between the provided longitudes.
     * If flight is going west, distance will be a negative value.
     */
    public static double getShortestDistance(double departureLongitude, double arrivalLongitude) {
        double distanceEast = arrivalLongitude - departureLongitude;
        double distanceWest = departureLongitude - arrivalLongitude;

        return Math.min(distanceEast, distanceWest);
    }
}