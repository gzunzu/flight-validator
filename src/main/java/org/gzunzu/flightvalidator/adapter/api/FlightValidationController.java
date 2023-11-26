package org.gzunzu.flightvalidator.adapter.api;

import lombok.RequiredArgsConstructor;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.FlightValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightValidationController {

    private final FlightValidatorService flightValidatorService;

    @GetMapping(name = "/validate")
    public ResponseEntity<ValidatedFlightDTO> validate(@RequestBody final Flight flight) {
        ValidatedFlightDTO validatedFlightDTO = this.flightValidatorService.validateFlight(flight);
        return ResponseEntity.ofNullable(validatedFlightDTO);
    }
}
