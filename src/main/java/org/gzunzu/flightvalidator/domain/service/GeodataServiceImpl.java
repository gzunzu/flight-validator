package org.gzunzu.flightvalidator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.gzunzu.flightvalidator.adapter.client.GeoApifyClient;
import org.gzunzu.flightvalidator.domain.configuration.GeoApifyConfiguration;
import org.gzunzu.flightvalidator.domain.dto.response.FeatureDTO;
import org.gzunzu.flightvalidator.domain.dto.response.PropertiesDTO;
import org.gzunzu.flightvalidator.domain.dto.response.ReverseGeodataResponseDTO;
import org.gzunzu.flightvalidator.domain.ports.GeodataService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeodataServiceImpl implements GeodataService {

    private final GeoApifyClient geoApifyClient;
    private final GeoApifyConfiguration geoApifyConfiguration;

    @Override
    public Optional<PropertiesDTO> getLocationData(final double lat, final double lon) {
        try {
            final ResponseEntity<ReverseGeodataResponseDTO> response = this.geoApifyClient.reverseGeocodeData(lat,
                    lon,
                    this.geoApifyConfiguration.getKey());
            return this.handleResponse(response, lat, lon);
        } catch (final Exception exception) {
            this.logGeoDataRequestException(exception, lat, lon);
        }
        return Optional.empty();
    }

    private Optional<PropertiesDTO> handleResponse(final ResponseEntity<ReverseGeodataResponseDTO> response, final double lat, final double lon) {
        if (response != null && response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            this.logGeoDataSuccessfulResponse(response, lat, lon);
            return CollectionUtils.emptyIfNull(response.getBody().getFeatures()).stream()
                    .map(FeatureDTO::getProperties)
                    .findFirst();
        } else {
            this.logGeoDataUnsuccessfulResponse(response, lat, lon);
        }
        return Optional.empty();
    }

    private void logGeoDataSuccessfulResponse(final ResponseEntity<ReverseGeodataResponseDTO> response, final double lat, final double lon) {
        log.debug("Geodata successfully retrieved for lat {}, pos {} via remote service HTTP request. Data: {}",
                lat,
                lon,
                response.getBody());
    }

    private void logGeoDataUnsuccessfulResponse(final ResponseEntity<ReverseGeodataResponseDTO> response, final double lat, final double lon) {
        log.warn("Unsuccessful attempt to get lat {}, pos {} data via remote service HTTP request. Remote service response: {} [{}]",
                lat,
                lon,
                response != null ? response.getStatusCode() : "NO HTTP STATUS AVAILABLE",
                response != null ? response.getStatusCode().value() : "NO HTTP STATUS CODE AVAILABLE");
    }

    private void logGeoDataRequestException(final Exception exception, final double lat, final double lon) {
        log.warn("Error while trying to get lat {}, pos {} data via remote service HTTP request. ",
                lat,
                lon,
                exception);
    }
}
