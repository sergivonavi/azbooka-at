package ru.azbooka.at.api.endpoints.auth;

import io.qameta.allure.Step;
import org.assertj.core.util.Strings;
import ru.azbooka.at.api.models.error.ErrorResponseDto;

import static ru.azbooka.at.api.specs.BaseSpecs.requestSpec;
import static ru.azbooka.at.api.specs.BaseSpecs.responseSpec;

public class LogoutApi {

    @Step("Запрашиваем выход из аккаунта с корректным токеном")
    public static void logout(String token) {
        doLogout(token, 200, Void.class);
    }

    @Step("Запрашиваем выход с некорректным токеном")
    public static ErrorResponseDto logoutWithError(String token) {
        return doLogout(token, 401, ErrorResponseDto.class);
    }

    private static <T> T doLogout(String token, int expectedStatus, Class<T> responseClass) {
        return (Strings.isNullOrEmpty(token) ? requestSpec() : requestSpec(token))
                .when()
                .get("/auth/logout")
                .then()
                .spec(responseSpec(expectedStatus))
                .extract()
                .as(responseClass);
    }
}
