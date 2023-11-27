package org.gzunzu.flightvalidator.adapter.api;

import lombok.RequiredArgsConstructor;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.FlightValidatorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/flights",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class FlightValidationController {

    private final FlightValidatorService flightValidatorService;

    @GetMapping(value = "/validate")
    public ResponseEntity<ValidatedFlightDTO> validate(@RequestBody @Valid final Flight flight) {
        final ValidatedFlightDTO validatedFlightDTO = this.flightValidatorService.validateFlight(flight);
        return ResponseEntity.ofNullable(validatedFlightDTO);
    }
}
