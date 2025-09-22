package ru.azbooka.at.api.assertions;

import io.qameta.allure.Step;
import ru.azbooka.at.api.models.UserResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAssertions {

    @Step("Проверяем, что поле username равно \"{expected}\"")
    public static void assertUsername(UserResponseDto actual, String expected) {
        assertThat(actual.getUsername()).isEqualTo(expected);
    }

    @Step("Проверяем, что поле email равно \"{expected}\"")
    public static void assertEmail(UserResponseDto actual, String expected) {
        assertThat(actual.getEmail()).isEqualTo(expected);
    }

    @Step("Проверяем, что поле first_name равно \"{expected}\"")
    public static void assertFirstName(UserResponseDto actual, String expected) {
        assertThat(actual.getFirstName()).isEqualTo(expected);
    }

    @Step("Проверяем, что поле last_name равно \"{expected}\"")
    public static void assertLastName(UserResponseDto actual, String expected) {
        assertThat(actual.getLastName()).isEqualTo(expected);
    }

    @Step("Проверяем, что поле avatar не равно \"{previous}\"")
    public static void assertAvatarChanged(UserResponseDto actual, String previous) {
        assertThat(actual.getAvatar())
                .isNotNull()
                .isNotEqualTo(previous);
    }
}
