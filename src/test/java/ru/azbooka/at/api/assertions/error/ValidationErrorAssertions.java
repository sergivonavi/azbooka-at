package ru.azbooka.at.api.assertions.error;

import io.qameta.allure.Step;
import ru.azbooka.at.api.models.error.ValidationErrorResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationErrorAssertions {

    @Step("Проверяем, что список ошибок detail не пустой")
    public static void assertDetailNotEmpty(ValidationErrorResponseDto response) {
        assertThat(response.getDetail()).isNotEmpty();
    }
}
