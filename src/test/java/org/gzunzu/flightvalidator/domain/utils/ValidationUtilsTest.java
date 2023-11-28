package org.gzunzu.flightvalidator.domain.utils;

import org.gzunzu.flightvalidator.domain.model.RuleValidationMessage;
import org.gzunzu.flightvalidator.domain.model.ValidationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ValidationUtilsTest {

    @Test
    void test_addValidationMessage() {
        final ValidationDTO validationDTO = ValidationDTO.builder()
                .validationMessages(new EnumMap<>(RuleValidationMessage.class))
                .build();

        ValidationUtils.addValidationMessage(validationDTO, RuleValidationMessage.VALID);

        assertThat(validationDTO.getValidationMessages()).isNotEmpty();
        assertThat(validationDTO.getValidationMessages().values()).contains(RuleValidationMessage.VALID.getMessage());
    }
}