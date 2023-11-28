package org.gzunzu.flightvalidator.adapter.client;

import org.gzunzu.flightvalidator.domain.dto.response.ReverseGeodataResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${integration.geoapify.name:geoapify}",
        url = "${integration.geoapify.url}",
        path = "${integration.geoapify.api}")
public interface GeoApifyClient {

    @GetMapping(value = "/geocode/reverse",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReverseGeodataResponseDTO> reverseGeocodeData(@RequestParam(name = "lat") final double lat,
                                                                 @RequestParam(name = "lon") final double lon,
                                                                 @RequestParam(name = "apiKey") final String apiKey);
}
