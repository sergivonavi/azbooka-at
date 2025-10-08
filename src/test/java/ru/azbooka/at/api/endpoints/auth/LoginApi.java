package ru.azbooka.at.api.endpoints.auth;

import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import ru.azbooka.at.api.models.error.ErrorResponseDto;
import ru.azbooka.at.api.models.LoginRequestDto;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.api.models.error.ValidationErrorResponseDto;
import ru.azbooka.at.config.TestUserConfig;

import static ru.azbooka.at.api.specs.BaseSpecs.requestSpec;
import static ru.azbooka.at.api.specs.BaseSpecs.responseSpec;

public class LoginApi {
    private static final TestUserConfig userConfig = ConfigFactory.create(TestUserConfig.class);

    @Step("Запрашиваем авторизацию пользователем из конфигурации")
    public static LoginResponseDto loginWithConfiguredUser() {
        return login(userConfig.email(), userConfig.password());
    }

    @Step("Запрашиваем авторизацию с корректными данными")
    public static LoginResponseDto login(String email, String password) {
        return doLogin(new LoginRequestDto(email, password), 200, LoginResponseDto.class);
    }

    @Step("Запрашиваем авторизацию с некорректными данными")
    public static ErrorResponseDto loginWithIncorrectCredentials(String email, String password) {
        return doLogin(new LoginRequestDto(email, password), 403, ErrorResponseDto.class);
    }

    @Step("Запрашиваем авторизацию с невалидным телом запроса")
    public static ValidationErrorResponseDto loginWithInvalidBody(String email, String password) {
        return doLogin(new LoginRequestDto(email, password), 422, ValidationErrorResponseDto.class);
    }

    @Step("Запрашиваем авторизацию без тела запроса")
    public static ValidationErrorResponseDto loginWithoutBody() {
        return doLogin(null, 422, ValidationErrorResponseDto.class);
    }

    private static <T> T doLogin(LoginRequestDto requestDto, int expectedStatus, Class<T> responseClass) {
        var spec = requestSpec();

        if (requestDto != null) {
            spec = spec.body(requestDto);
        }

        return spec
                .when()
                .post("/auth/login")
                .then()
                .spec(responseSpec(expectedStatus))
                .extract().as(responseClass);
    }
}
