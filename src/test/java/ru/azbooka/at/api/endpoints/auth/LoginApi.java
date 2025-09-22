package ru.azbooka.at.api.endpoints.auth;

import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import ru.azbooka.at.api.models.LoginRequestDto;
import ru.azbooka.at.api.models.LoginResponseDto;
import ru.azbooka.at.config.TestUserConfig;

import static ru.azbooka.at.api.specs.BaseSpecs.requestSpec;

public class LoginApi {
    private static final TestUserConfig userConfig = ConfigFactory.create(TestUserConfig.class);

    @Step("Запрашиваем авторизацию пользователем из конфигурации")
    public static LoginResponseDto loginWithConfiguredUser() {
        return login(userConfig.email(), userConfig.password());
    }

    public static LoginResponseDto login(String email, String password) {
        LoginRequestDto requestDto = new LoginRequestDto(email, password);

        return requestSpec()
                .body(requestDto)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponseDto.class);
    }
}
