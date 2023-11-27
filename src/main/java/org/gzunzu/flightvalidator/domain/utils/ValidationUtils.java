package org.gzunzu.flightvalidator.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;

import java.util.EnumMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

    public static void addValidationMessage(final ValidatedFlightDTO flightDTO,
                                            final RuleValidationMessage rule,
                                            final Object... messageParams) {
        final String message = String.format(rule.getMessage(), messageParams);
        flightDTO.getValidationMessages().put(rule, message);
    }

    public static ValidatedFlightDTO getFailedValidationResponse(final String exceptionMessage) {
        final String message = String.format(RuleValidationMessage.UNPROCESSABLE_ENTITY.getMessage(), exceptionMessage);
        return ValidatedFlightDTO.builder()
                .validationMessages(new EnumMap<>(Map.of(RuleValidationMessage.UNPROCESSABLE_ENTITY, message)))
                .feasible(false)
                .build();
    }
}
