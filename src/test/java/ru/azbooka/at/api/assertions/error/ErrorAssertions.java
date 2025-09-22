package ru.azbooka.at.api.assertions.error;

import io.qameta.allure.Step;
import ru.azbooka.at.api.models.error.ErrorResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorAssertions {

    @Step("Проверяем, что в detail текст ошибки равен \"{detail}\"")
    public static void assertErrorDetail(ErrorResponseDto errorResponse, String detail) {
        assertThat(errorResponse.getDetail()).isEqualTo(detail);
    }

    public static class Detail {
        public static final String INVALID_CREDENTIALS = "Invalid credentials";
        public static final String INVALID_TOKEN = "Invalid token supplied";
        public static final String UNAUTHORIZED = "Unauthorized";
    }
}
