package org.gzunzu.flightvalidator.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gzunzu.flightvalidator.domain.model.Flight;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatusCode status,
                                                                  final WebRequest request) {
        final Flight providedFlight = (Flight) ex.getTarget();
        final ValidatedFlightDTO errorResponse = new ValidatedFlightDTO(providedFlight, null);
        errorResponse.setFeasible(false);

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this.mapToErrorFieldLine())
                .collect(Collectors.toList());

        errorResponse.getValidationMessages().put(RuleValidationMessage.UNPROCESSABLE_ENTITY, errors.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    private Function<FieldError, String> mapToErrorFieldLine() {
        return (final FieldError fieldError) -> fieldError.getField()
                .concat(": ")
                .concat(StringUtils.defaultIfEmpty(fieldError.getDefaultMessage(), "not valid."));
    }
}