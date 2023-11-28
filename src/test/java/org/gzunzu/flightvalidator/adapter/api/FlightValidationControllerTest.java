package org.gzunzu.flightvalidator.adapter.api;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;
import org.gzunzu.flightvalidator.domain.ports.FlightValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Slf4j
@ExtendWith(MockitoExtension.class)
class FlightValidationControllerTest {

    @InjectMocks
    private FlightValidationController controller;
    @Mock
    private FlightValidatorService flightValidatorService;

    @Test
    void test_validate_nullResponse() {
        final Flight flight = mock(Flight.class);

        when(this.flightValidatorService.validateFlight(eq(flight)))
                .thenReturn(null);

        final ResponseEntity<ValidationDTO> result = this.controller.validate(flight);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isNull();
    }

    @Test
    void test_validate_notNullResponse() {
        final Flight flight = mock(Flight.class);
        final ValidationDTO validationDTO = mock(ValidationDTO.class);

        when(this.flightValidatorService.validateFlight(eq(flight)))
                .thenReturn(validationDTO);

        final ResponseEntity<ValidationDTO> result = this.controller.validate(flight);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(validationDTO);
    }
}