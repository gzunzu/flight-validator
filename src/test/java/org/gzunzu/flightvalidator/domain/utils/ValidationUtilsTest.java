package org.gzunzu.flightvalidator.domain.utils;

import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidatedFlightDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ValidationUtilsTest {

    @Test
    void test_addValidationMessage() {
        final ValidatedFlightDTO validatedFlightDTO = ValidatedFlightDTO.builder()
                .validationMessages(new EnumMap<>(RuleValidationMessage.class))
                .build();

        ValidationUtils.addValidationMessage(validatedFlightDTO, RuleValidationMessage.VALID);

        assertThat(validatedFlightDTO.getValidationMessages()).isNotEmpty();
        assertThat(validatedFlightDTO.getValidationMessages().values()).contains(RuleValidationMessage.VALID.getMessage());
    }
}