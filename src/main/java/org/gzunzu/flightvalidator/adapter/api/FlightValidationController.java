package org.gzunzu.flightvalidator.adapter.api;

import lombok.RequiredArgsConstructor;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.gzunzu.flightvalidator.domain.ports.FlightValidatorService;
import org.gzunzu.flightvalidator.domain.utils.ValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
        ValidatedFlightDTO validatedFlightDTO = this.flightValidatorService.validateFlight(flight);
        return ResponseEntity.ofNullable(validatedFlightDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidatedFlightDTO> handleValidationException(MethodArgumentNotValidException ex) {
        final ValidatedFlightDTO failedValidation = ValidationUtils.getFailedValidationResponse(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(failedValidation);
    }
}
