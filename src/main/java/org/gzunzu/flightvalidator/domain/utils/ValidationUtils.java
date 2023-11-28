package org.gzunzu.flightvalidator.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

    public static void addValidationMessage(final ValidationDTO flightDTO,
                                            final RuleValidationMessage rule,
                                            final Object... messageParams) {
        final String message = String.format(rule.getMessage(), messageParams);
        flightDTO.getValidationMessages().put(rule, message);
    }
}
