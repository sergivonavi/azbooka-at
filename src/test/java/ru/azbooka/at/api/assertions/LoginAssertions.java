package ru.azbooka.at.api.assertions;

import io.qameta.allure.Step;
import ru.azbooka.at.api.models.LoginResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginAssertions {

    @Step("Проверяем, что поле access_token не пустое")
    public static void assertAccessTokenIsNotNullOrEmpty(LoginResponseDto loginResponse) {
        assertThat(loginResponse.getAccessToken())
                .as("Проверка access_token")
                .isNotNull()
                .isNotEmpty();
    }

    @Step("Проверяем, что поле refresh_token не пустое")
    public static void assertRefreshTokenIsNotNullOrEmpty(LoginResponseDto loginResponse) {
        assertThat(loginResponse.getRefreshToken())
                .as("Проверка refresh_token")
                .isNotNull()
                .isNotEmpty();
    }

    @Step("Проверяем, что поле token_type равно \"Bearer\"")
    public static void assertTokenTypeIsBearer(LoginResponseDto loginResponse) {
        assertThat(loginResponse.getTokenType())
                .as("Проверка token_type")
                .isEqualTo("Bearer");
    }
}
