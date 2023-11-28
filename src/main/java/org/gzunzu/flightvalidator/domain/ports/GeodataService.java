package org.gzunzu.flightvalidator.domain.ports;

import org.gzunzu.flightvalidator.domain.dto.response.PropertiesDTO;

import java.util.Optional;

public interface GeodataService {

    /**
     * Gets location data via remote service HTTP request, given latitude ans longitude points.
     *
     * @param lat latitude value.
     * @param lon longitude value.
     * @return an optional containing an object with many location information, if any information found.
     */
    Optional<PropertiesDTO> getLocationData(final double lat, final double lon);
}
